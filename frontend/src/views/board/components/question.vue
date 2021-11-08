<template lang="">
  <div class="board-question">
    <div class="question-title">{{ title }}</div>
    <div class="question-createdAt">{{ createdAt }}</div>
    <div class="question-user">
      <div class="question-user-image">
        <img :src="state.user.image" class="question-user-image-circle" />
      </div>
      <div class="question-user-nickName">
        {{ state.user.nickName }}
      </div>
    </div>
    <div class="question-description" id="viewer"></div>
    <!-- 코멘트 입력하기 -->
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
    user: String,
  },
  setup(props) {
    console.log(props);
    console.log(props.user);
    const state = reactive({
      user: JSON.parse(props.user),
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
