<template>
  <div class="left-wrap">
    <div class="open-left-wrap">
      <div class="board-nav" ref="scrollTargetRef">
        <div class="board-btn-info">게시판소개</div>
        <div class="board-search">게시판검색</div>
        <div class="board-title-list">
          <q-infinite-scroll
            @load="load"
            :offset="300"
            :scroll-target="scrollTargetRef"
          >
            <list-item
              class="board-title-list-item"
              :key="index"
              :item="item"
              v-for="(item, index) in question_list"
            />
            <template v-slot:loading>
              <div class="row justify-center q-my-md">
                <q-spinner-dots color="primary" size="40px" />
              </div>
            </template>
          </q-infinite-scroll>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import "@/styles/board.scss";
import { ref, reactive, onMounted } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import ListItem from "./components/ListItem.vue";

export default {
  name: "board-left",
  components: { ListItem },
  setup() {
    const store = useStore();
    const router = useRouter();
    const question_list = ref([]);
    const state = reactive({
      word: "",
      question_list: [],
    });
    const scrollTargetRef = ref(null);
    const mvInfo = () => {
      router.push({ name: "board-info" });
    };
    const mvQuestion = () => {
      router.push({ name: "board-question" });
    };

    const load = (index, done) => {
      setTimeout(() => {
        var size = question_list.value.length;
        var max_size = state.question_list.length;
        if (size + 5 <= max_size) {
          console.log("나 아직 작아 맥스보다", size, max_size);
          question_list.value.push(
            ...state.question_list.slice(size, size + 5)
          );
        } else {
          console.log("난 존나큰데 ㅋㅋ");
          for (var i = size; i < max_size; i++) {
            question_list.value.push(state.question_list[i]);
          }
          done(true);
        }

        done();
      }, 500);
    };

    onMounted(() => {
      store
        .dispatch("root/requestBoardList")
        .then(
          (response) => {
            state.question_list = response.data;
            question_list.value.push(...state.question_list.slice(0, 7));
            console.log(question_list.value);
          },
          (error) => {
            console.log(error.response.data);
          }
        )
        .catch((error) => {
          console.log(error);
        });
    });

    const questionItems = (question) => {
      router.push({
        name: "list-item",
        params: {
          id: question.id,
          description: question.description,
          title: question.title,
          createdAt: question.createdAt,
          user: question.user,
        },
      });
    };

    const questionList = () => {
      store
        .dispatch("root/requestBoardList")
        .then(
          (response) => {
            console.log(response);
          },
          (error) => {
            console.log(error.response.data);
          }
        )
        .catch((error) => {
          console.log(error);
        });
    };

    return {
      question_list,
      state,
      scrollTargetRef,

      mvInfo,
      mvQuestion,
      load,
      questionList,
      questionItems,
    };
  },
};
</script>
<style></style>
