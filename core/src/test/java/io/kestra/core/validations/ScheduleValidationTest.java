package io.kestra.core.validations;

import io.kestra.core.junit.annotations.KestraTest;
import org.junit.jupiter.api.Test;
import io.kestra.plugin.core.trigger.Schedule;
import io.kestra.core.models.validations.ModelValidator;
import io.kestra.core.utils.IdUtils;

import jakarta.inject.Inject;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

@KestraTest
class ScheduleValidationTest {
    @Inject
    private ModelValidator modelValidator;

    @Test
    void cronValidation() throws Exception {
        Schedule build = Schedule.builder()
            .id(IdUtils.create())
            .type(Schedule.class.getName())
            .cron("* * * * *")
            .build();

        assertThat(modelValidator.isValid(build).isEmpty(), is(true));

        build = Schedule.builder()
            .type(Schedule.class.getName())
            .cron("$ome Inv@lid Cr0n")
            .build();

        assertThat(modelValidator.isValid(build).isPresent(), is(true));
        assertThat(modelValidator.isValid(build).get().getMessage(), containsString("invalid cron expression"));
    }

    @Test
    void nicknameValidation() throws Exception {
        Schedule build = Schedule.builder()
            .id(IdUtils.create())
            .type(Schedule.class.getName())
            .cron("@hourly")
            .build();

        assertThat(modelValidator.isValid(build).isEmpty(), is(true));
    }

    @Test
    void withSecondsValidation() throws Exception {
        Schedule build = Schedule.builder()
            .id(IdUtils.create())
            .type(Schedule.class.getName())
            .withSeconds(true)
            .cron("* * * * * *")
            .build();

        assertThat(modelValidator.isValid(build).isEmpty(), is(true));

        build = Schedule.builder()
            .id(IdUtils.create())
            .type(Schedule.class.getName())
            .cron("* * * * * *")
            .build();

        assertThat(modelValidator.isValid(build).isPresent(), is(true));
        assertThat(modelValidator.isValid(build).get().getMessage(), containsString("invalid cron expression"));
    }

    @Test
    void lateMaximumDelayValidation()  {
        Schedule build = Schedule.builder()
            .id(IdUtils.create())
            .type(Schedule.class.getName())
            .cron("* * * * *")
            .lateMaximumDelay(Duration.ofSeconds(10))
            .build();

        assertThat(modelValidator.isValid(build).isPresent(), is(false));
    }

    @Test
    void intervalValidation() {
        Schedule build = Schedule.builder()
            .id(IdUtils.create())
            .type(Schedule.class.getName())
            .cron("* * * * *")
            .interval(Duration.ofSeconds(5))
            .build();


        assertThat(modelValidator.isValid(build).isPresent(), is(true));
        assertThat(modelValidator.isValid(build).get().getMessage(), containsString("interval: must be null"));

    }
}