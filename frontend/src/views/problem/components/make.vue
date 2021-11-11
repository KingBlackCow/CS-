<template>
  <div class="makeProb-wrap">
    <div class="makeProb-title">문제만들기</div>
    <div class="">
      <form class="makeProb-form" v-on:submit.prevent autocomplete="off">
        <div>
          <label class="label" for="category">문제 카테고리</label>
          <select v-model="state.problem.categoryId" id="category">
            <option
              v-for="(category, idx) in state.categories"
              :key="idx"
              :value="category.id"
            >
              {{ category.name }}
            </option>
          </select>
        </div>
        <div>
          <label class="label" for="desc">문제</label>
          <input id="desc" v-model="state.problem.description" />
        </div>
        <div class="answer-wrap">
          <label class="label" for="answerinput">정답</label>
          <div>
            <div>
              <input
                id="answerinput"
                v-model="temp"
                @keydown.enter.prevent="addInput"
              />
              <input
                class="addBtn"
                type="button"
                value="추가"
                @click="addInput"
              />
            </div>
            <div
              v-for="(answer, index) in answers"
              :key="answer.id"
              class="answer"
            >
              <div class="ans-wrap">
                {{ answer.ans }}
                <button @click="removeAns(index)">삭제</button>
              </div>
            </div>
          </div>
        </div>
      </form>
      <button class="makeBtn" @click="makeProblem">생성하기</button>
    </div>
  </div>
</template>

<script>
import { useStore } from "vuex";
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";

export default {
  setup() {
    const store = useStore();
    const router = useRouter();
    const temp = ref("");
    const answers = ref([]);
    const state = reactive({
      categories: store.getters["root/getCategories"],
      problem: {
        userId: null,
        categoryId: null,
        description: null,
        answers: [],
      },
    });

    const addInput = () => {
      if (temp.value == "") {
        alert("답을 입력해주세요");
      } else {
        answers.value.push({
          id: Date.now(),
          ans: temp.value,
        });
        console.log(answers.value);
        temp.value = "";
      }
    };

    const removeAns = (index) => {
      answers.value.splice(index, 1);
    };

    const makeProblem = () => {
      if (state.problem.categoryId == null) {
        alert("카테고리를 지정해주세요.");
      } else if (state.problem.description == null) {
        alert("문제설명은 필수입니다.");
      } else if (
        state.problem.description.length <= 4 ||
        state.problem.description.length > 400
      ) {
        alert(
          "문제 길이는 5글자 이상 400글자 이하입니다. 현재 글자는" +
            state.problem.description.length +
            "글자입니다"
        );
      } else if (answers.value.length == 0) {
        if (temp.value == "") {
          alert("정답 입력은 필수입니다.");
        } else {
          alert("정답 추가 버튼을 눌러주세요");
        }
      } else {
        state.problem.userId = JSON.parse(localStorage.getItem("userInfo")).id;
        for (var i = 0; i < answers.value.length; i++) {
          state.problem.answers.push(answers.value[i].ans);
        }
        store
          .dispatch("root/requsetProblemCreate", state.problem)
          .then((response) => {
            console.log(response);
            alert("문제 생성이 완료되었습니다. 승인 후 등록됩니다.");
            state.problem.categoryId = null;
            state.problem.description = null;
            state.problem.answers = [];
            temp.value = "";
            answers.value = [];
            document.getElementById("answerinput").value = "";
            store.dispatch("root/requestAllProblem").then((response) => {
              store.commit("root/setAllProblemNum", response.data);
            });
            router.push({ name: "problem-info" });
          })
          .catch((err) => {
            console.log(err);
          });
      }
    };

    return {
      temp,
      answers,
      state,
      makeProblem,
      addInput,
      removeAns,
    };
  },
};
</script>
