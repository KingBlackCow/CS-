<template>
  <div class="left-wrap">
    <div class="column justify-evenly">
      <div id="searchWrap" classs="col-4">
        <input type="text" name="word" v-model="state.word" />
      </div>
      <div class="q-list column justify-evenly" style="height: 150px">
        <div id="listItemMargin">
          <list-item
            v-for="(item, index) in state.questionList"
            :key="index"
            :item="item"
          />
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import { reactive, onMounted } from "vue";
import ListItem from "./components/ListItem.vue";

export default {
  name: "board-left",
  components: { ListItem },
  setup() {
    const store = useStore();
    const router = useRouter();
    const state = reactive({
      word: "",
      // questionList: computed(() => store.getters["root/requestBoardList"]),
      questionList: [],
    });
    const mvBoardInfo = () => {
      router.push({ name: "board-info" });
    };
    const mvQuestion = () => {
      router.push({ name: "board-question" });
    };

    // computed(() => {
    // const filtered = () => {
    //   var title = state.word.trim();
    //   return this.BoardList.filter(function (item) {
    //     if (item.title.indexOf(title) > -1) {
    //       return true;
    //     }
    //   });
    // };
    // console.log(filtered());
    // filtered() {
    //       // var cname = word.trim();
    //       var title = state.word.trim();
    //       return this.BoardList.filter(function (item) {
    //           if (item.title.indexOf(title) > -1) {
    //               return true;
    //           }
    //       });
    //   },
    // }),
    onMounted(() => {
      store
        .dispatch("root/requestBoardList")
        .then(
          (response) => {
            console.log("success");
            state.questionList = response.data;
            console.log(state.questionList);
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
      mvBoardInfo,
      mvQuestion,
      questionList,
      questionItems,
      // filtered,
      state,
    };
  },
};
</script>
<style>
#listItemMargin {
  margin-top: 100px;
}
#mapWrap,
#infoWrap {
  position: absolute;
  top: 80px;
  bottom: 0;
}
#mapWrap {
  position: absolute;
  left: 30%;
  right: 0;
  overflow: hidden;
}
#infoWrap {
  left: 0;
  right: 70%;
}
#infoSide {
  position: fixed;
  right: 10px;
  bottom: 10px;
}
#infoList {
  overflow: auto;
  height: 90%;
  overflow-x: hidden;
}
#searchWrap {
  width: 60%;
  margin: 20px auto 40px;
}
#searchWrap > input {
  border: none;
  outline: none;
  border-bottom: 1px solid black;
  width: 100%;
  /* background-image: url(../assets/search.png); */
  background-position: 0px 2px;
  background-repeat: no-repeat;
  background-size: 22px;
  padding-left: 32px;
  box-sizing: border-box;
}
#msg {
  position: absolute;
  bottom: 0;
  right: 0;
  width: 400px;
  height: 440px;
  border-radius: 8px;
  display: none;
  z-index: 100;
  background-color: rgba(255, 255, 255, 0.5);
}
#mark:hover > #msg {
  /* color: tomato; */
  display: block;
}
#mark:hover > #ques {
  display: none;
}
#ques {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  display: block;
  font-size: 1.6em;
  font-weight: 900;
  color: black;
  border: 1px solid black;
  background-color: rgba(255, 255, 255, 0.5);
  text-align: center;
  line-height: 60px;
}
#msg > h2 {
  font-weight: 700;
  color: black;
  font-size: 1.4em;
  margin-top: 32px;
  text-align: center;
}
#legendTemplate1 {
  position: absolute !important;
  left: 50%;
  transform: translateX(-50%);
}
</style>
