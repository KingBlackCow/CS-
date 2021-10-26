import axios from "@/lib/axios";

// http://localhost:8080/swagger-ui.html

/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 유저 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
// 생성하기
export function requsetUserRegist({ state }, payload) {
  console.log("requsetUserRegist", state, payload);
  const url = "/user";
  const body = payload;
  return axios.post(url, body);
}
// 로그인하기
export function requestUserLogin({ state }, payload) {
  console.log("requestUserLogin", state, payload);
  const url = "/user/login";
  const body = payload;
  return axios.post(url, body);
}
// 유저정보 가져오기
export function requsetUserInfo({ state }, payload) {
  console.log("requsetUserInfo", state, payload);
  const url = "/user/me";
  const body = payload;
  return axios.get(url, body);
}
// 유저정보 리스트 가져오기
export function requestUserList({ state }, payload) {
  console.log("requestUserList", state, payload);
  const url = "/user/all";
  const body = payload;
  return axios.get(url, body);
}

/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 카테고리 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
// 모든 카테고리 정보 가져오기
export function requsetCategoryCreate({ state }, payload) {
  console.log("requsetCategoryCreate", state, payload);
  const url = "/category";
  return axios.get(url);
}

/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 문제 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
// 문제만들기
export function requsetProblemCreate({ state }, payload) {
  console.log("requsetProblemCreate", state, payload);
  const url = "/problem/make";
  const body = payload;
  return axios.post(url, body);
}
// 카테고리별 문제 리스트 가져오기
export function requestProblemList({ state }, payload) {
  console.log("requestProblemList", state, payload);
  const url = `/problem/${payload.id}}`;
  return axios.get(url);
}
// 문제 정답 맞추기
export function requestProblemCheckAnswer({ state }, payload) {
  console.log("requestProblemCheckAnswer", state, payload);
  const url = "/problem/checkanswer";
  const body = payload;
  return axios.post(url, body);
}
// 승인 대기중인 문제 리스트 가져오기
export function requestProblemWait({ state }, payload) {
  console.log("requestProblemWait", state, payload);
  const url = "/problem/wait";
  const body = payload;
  return axios.get(url, body);
}
// 관리자가 문제상태 변경하기
export function requestProblemEvaluate({ state }, payload) {
  console.log("requestProblemEvaluate", state, payload);
  const url = "/problem/evaluate";
  const body = payload;
  return axios.post(url, body);
}

/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 게시판 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
// 게시판 한개 가져오기
export function requsetBoardInfo({ state }, payload) {
  console.log("requsetBoardInfo", state, payload);
  const url = `/board/${payload.id}`;
  return axios.get(url);
}
// 게시판 리스트 가져오기
export function requestBoardList({ state }, payload) {
  console.log("requestBoardList", state, payload);
  const url = "/board";
  return axios.get(url);
}
