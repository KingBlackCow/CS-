<template lang="">
  <div>
    <div class="text-body1 text-weight-bolder title">{{ title }}</div>
    <hr class="my-hr3" />
    <div class="board-content">{{ description }}</div>
    <!-- </q-splitter> -->
    <!-- <div>
      <div>
        <div class="q-pa-md q-gutter-sm">
          <q-banner rounded class="bg-grey-3">
            <span class="text-body1 text-weight-bolder">{{ title }}</span>
          </q-banner>
        </div>
        <div>
          {{ description }}
        </div>
        <div>
          {{ description }}
        </div>
      </div>
    </div> -->
  </div>
</template>
<script>
export default {
  name: "board-question",
  props: {
    // item: { type: Object },
    id: Number,
    title: String,
    description: String,
    createdAt: String,
    user: Object,
  },
  setup(props) {
    console.log("보드 뷰");
    console.log(props.title);
    return {};
  },
};
</script>
<style>
.my-hr3 {
  border: 0;
  height: 3px;
  background: #ccc;
}

.title {
  margin: 13px;
}

.board-content {
  margin: 10px;
}
</style>
