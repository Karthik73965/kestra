<template>
    <div class="p-4 card">
        <div class="d-flex pb-2 justify-content-between">
            <div class="d-flex align-items-center">
                <el-tooltip
                    v-if="tooltip"
                    :content="tooltip"
                    popper-class="dashboard-card-tooltip"
                >
                    <component :is="icon" class="me-2 fs-4 icons" />
                </el-tooltip>
                <component v-else :is="icon" class="me-2 fs-4 icons" />

                <p class="m-0 fs-6 label">
                    {{ label }}
                </p>
            </div>

            <RouterLink :to="redirect" class="d-flex align-items-center">
                <TextSearchVariant class="fs-4 icons url" />
            </RouterLink>
        </div>
        <div v-if="loading" class="loading-skeleton">
            <div class="skeleton-line" />
        </div>
        <p v-else class="m-0 fs-2 fw-bold">
            {{ value }}
        </p>
    </div>
</template>

<script setup>
    import TextSearchVariant from "vue-material-design-icons/TextSearchVariant.vue";

    defineProps({
        icon: {
            type: Object,
            required: true,
        },
        label: {
            type: String,
            required: true,
        },
        tooltip: {
            type: String,
            default: undefined,
        },
        value: {
            type: [String, Number],
            required: true,
        },
        redirect: {
            type: Object,
            required: true,
        },
        loading: {
            type: Boolean,
            default: false
        }
    });
</script>

<style lang="scss" scoped>
@import "@kestra-io/ui-libs/src/scss/variables";

.card {
    & .icons {
        color: $secondary;

        &.url {
            color: #7e719f;
        }
    }

    & .label {
        color: $gray-700;

        html.dark & {
            color: $gray-300;
        }
    }

    & .loading-skeleton {
        height: 2rem;
        display: flex;
        align-items: center;
    }

    & .skeleton-line {
        height: 1.5rem;
        width: 60%;
        background: linear-gradient(
            90deg,
            rgba(190, 190, 190, 0.2) 25%,
            rgba(129, 129, 129, 0.24) 37%,
            rgba(190, 190, 190, 0.2) 63%
        );
        background-size: 400% 100%;
        animation: shimmer 1.4s ease infinite;
        border-radius: 4px;

        html.dark & {
            background: linear-gradient(
                90deg,
                rgba(255, 255, 255, 0.1) 25%,
                rgba(255, 255, 255, 0.15) 37%,
                rgba(255, 255, 255, 0.1) 63%
            );
            background-size: 400% 100%;
        }
    }
}

@keyframes shimmer {
    0% {
        background-position: 100% 50%;
    }
    100% {
        background-position: 0 50%;
    }
}
</style>

<style lang="scss">
.dashboard-card-tooltip {
    width: 300px;
}
</style>