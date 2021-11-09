package com.js.freeproject.domain.scorehistory.domain;

import lombok.Getter;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class ScoreHistoryResponse {

	private Integer score;
	private String createDate;
	static SimpleDateFormat date = new SimpleDateFormat("MM/dd");

	public ScoreHistoryResponse(ScoreHistory scoreHistory) {
		this.score = scoreHistory.getScore();
		this.createDate = date.format(scoreHistory.getCreatedate())+"\n"+scoreHistory.getCategory().getName();
	}
}
