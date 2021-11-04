<template lang="">
  <div class="q-item">
    <div class="q-item-section">
      <q-btn @click="getBoardInfo()" style="width: 100%">
        <div>
          <div id="title">
            <q-item-label>{{ item.title }}</q-item-label>
            <q-item-label caption lines="2">{{ item.createdAt }}</q-item-label>
          </div>
        </div>
        <!-- <button v-if="show" @click=""></button>
      <button v-else @click=""></button> -->
      </q-btn>
    </div>
  </div>
</template>
<script>
import { useRouter } from "vue-router";
// import BoardInfo from "./boardinfo.vue";

export default {
  name: "list-item",
  // components: { BoardInfo },
  props: {
    item: { type: Object },
  },
  setup(props) {
    console.log(props.item);
    const router = useRouter();

    const getBoardInfo = () => {
      console.log("상세보기 데이터");
      console.log(props.item.title);
      console.log(props.item.user);
      router.push({
        name: "board-question",
        params: {
          id: props.item.id,
          title: props.item.title,
          description: props.item.description,
          createdAt: props.item.createdAt,
          user: props.item.user,
        },
      });
    };
    return {
      useRouter,
      getBoardInfo,
    };
  },
};
</script>
<style>
/* #listItem {
  background-color: #f6f6f6;
  width: 90%;
  margin: 30px;
} */

#listItem > button {
  width: 100%;
  padding: 20px 0;
  border-radius: 0;
  background-color: transparent;
  border: none;
  padding-right: 20px;
  padding-left: 24px;
}
/*
#listItem > button > button {
  float: right;
}
#listItem > button > div {
  float: left;
}
#listItem > button {
  content: "";
  display: block;
  clear: both;
}
#aptName {
  color: black;
  font-weight: bold;
  font-size: 1.1em;
  text-align: left;
}
#aptPrice {
  color: #909090;
  text-align: left;
}
button > img {
  width: 24px;
}
th {
  color: black;
}
.infoTable {
  width: 100%;
  margin-bottom: 15px;
}
.infoTable th {
  color: black;
  background-color: transparent;
  width: 50%;
} */
</style>
