<template>
  <div v-if="state.error != ''">{{ state.error }}</div>
  <div v-else>
    <div class="board-question" v-if="state.question != null">
      <div class="question-title">{{ state.question.title }}</div>
      <div class="question-createdAt">{{ state.question.createdAt }}</div>
      <div class="question-user">
        <div class="question-user-image">
          <img
            :src="state.question.user.image"
            class="question-user-image-circle"
          />
        </div>
        <div class="question-user-nickName">
          {{ state.question.user.nickName }}
        </div>
      </div>
      <hr />
      <div class="question-info">
        <div class="question-description" id="viewer"></div>
        <!-- 코멘트 입력하기 -->
        <hr />
        <div class="question-comment">
          <question-answer
            class="question-comment-info"
            v-for="(answer, index) in state.question.answerComment"
            :key="index"
            :answer="answer"
            :comment="state.question.coComment"
          ></question-answer>
          <div style="max-width: 900px" class="row justify-center">
            <q-input
              v-model="answer"
              label="댓글달기"
              @keyup.enter="
                onSubmit();
                $event.target.blur();
              "
            ></q-input>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import "@/styles/board.scss";
import "@toast-ui/editor/dist/toastui-editor-viewer.css";
import Viewer from "@toast-ui/editor/dist/toastui-editor-viewer";
import QuestionAnswer from "./components/answer.vue";

import { computed, reactive, onUpdated } from "vue";
import { useRoute } from "vue-router";
import { useStore } from "vuex";

export default {
  name: "board-question",
  components: {
    QuestionAnswer,
  },
  data() {
    return {
      answer: "",
    };
  },
  methods: {
    onSubmit() {
      const payload = {
        content: this.answer,
        userId: localStorage.getItem("userId"),
        parentId: 0,
        boardId: this.$route.params.id,
      };
      this.$store
        .dispatch("root/requestWriteComment", payload)
        .then((response) => {
          console.log(response);
        })
        .catch((error) => {
          console.log(error.response.data);
        });
      this.$router.go();
    },
  },
  setup() {
    const route = useRoute();
    const store = useStore();

    const state = reactive({
      error: "",
      question: null,
      viewer: null,
      user: computed(() => store.getters["root/getUser"]),
    });

    const getQuestion = () => {
      store
        .dispatch("root/requsetBoardInfo", route.params.id)
        .then(
          (response) => {
            console.log(response.data);
            state.question = response.data;
          },
          (error) => {
            state.error = error.response.data.message;
          }
        )
        .catch((error) => {
          console.log(error);
        });
    };
    getQuestion();

    onUpdated(() => {
      state.viewer = new Viewer({
        el: document.querySelector("#viewer"),
        initialValue: state.question.description,
        height: document.querySelector(".question-info").clientHeight,
      });
    });

    return {
      state,
    };
  },
};
</script>
<style></style>
