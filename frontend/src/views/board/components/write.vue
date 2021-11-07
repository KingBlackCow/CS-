<template>
  <div class="board-write">
    <q-input
      class="board-write-form"
      v-model="state.title"
      counter
      maxlength="125"
      label="제목"
    ></q-input>
    <div class="board-write-editor" ref="ref_editor"></div>
    <div class="board-write-btn">
      <div @click="previewBoard">미리보기</div>
      <div @click="writeBoard">작성</div>
    </div>
    <div id="viewer"></div>
  </div>
</template>

<script>
import "@toast-ui/editor/dist/toastui-editor.css";
import "@toast-ui/editor/dist/toastui-editor-viewer.css";

import Editor from "@toast-ui/editor";
import Viewer from "@toast-ui/editor/dist/toastui-editor-viewer";

import { ref, reactive, onMounted } from "vue";
import { useStore } from "vuex";

export default {
  name: "board-write",
  setup() {
    const store = useStore();
    const state = reactive({
      title: "",
      editor: null,
      viewer: null,
    });

    const ref_editor = ref(null);

    onMounted(() => {
      state.editor = new Editor({
        el: ref_editor.value,
        height:
          document.getElementsByClassName("board-write-editor").clientHeight,
        initialEditType: "markdown",
        previewStyle: "vertical",
      });
    });
    const previewBoard = () => {
      state.viewer = new Viewer({
        el: document.querySelector("#viewer"),
        initialValue: state.editor.getMarkdown(),
      });
    };

    const writeBoard = () => {
      var editor_text = state.editor.getMarkdown();
      var user = JSON.parse(localStorage.getItem("userInfo"));
      store
        .dispatch("root/requestBoardWrite", {
          id: user.id,
          title: state.title,
          description: editor_text,
        })
        .then((response) => {
          console.log(response);
          /* 내가 작성한 글로 보내기 */
          /* 마이페이지로 보내기 */
          /* 게시판 소개 페이지로 보내기 */
        })
        .catch((error) => {
          console.log(error);
        });
    };

    return {
      state,
      ref_editor,
      onMounted,
      previewBoard,
      writeBoard,
    };
  },
};
</script>
