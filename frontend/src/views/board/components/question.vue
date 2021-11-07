<template lang="">
  <div class="board-question">
    <div class="question-title">{{ title }}</div>
    <div class="question-user">{{ user.name }} {{ user.nickName }}</div>
    <div class="question-createAt">{{ createdAt }}</div>
    <div class="question-description" id="viewer"></div>
  </div>
</template>
<script>
import "@/styles/board.scss";
import "@toast-ui/editor/dist/toastui-editor-viewer.css";
import Viewer from "@toast-ui/editor/dist/toastui-editor-viewer";

import { reactive, onMounted, watch } from "vue";

export default {
  name: "board-question",
  props: {
    id: Number,
    title: String,
    description: String,
    createdAt: String,
    user: Object,
  },
  setup(props) {
    const state = reactive({
      viewer: null,
    });
    watch(
      () => props.description,
      () => {
        state.viewer = new Viewer({
          el: document.querySelector("#viewer"),
          initialValue: props.description,
        });
      }
    );
    onMounted(() => {
      state.viewer = new Viewer({
        el: document.querySelector("#viewer"),
        initialValue: props.description,
      });
    });

    return { state };
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
