package com.mygdx.game;

import java.util.ArrayList;

public class WaveInfo {

    int levelNum;
    int peopleNum;
    int securityNum;
    int copNum;
    int emtNum;

    int BackUpTime;

    int healthPackNum;
    int antidoes;
    int cureZones;

    public WaveInfo(int levelNum, int peopleNum, int securityNum, int copNum, int emtNum) {
        this.levelNum = levelNum;
        this.peopleNum = peopleNum;
        this.securityNum = securityNum;
        this.copNum = copNum;
        this.emtNum = emtNum;
        this.BackUpTime = 120;
    }

    public WaveInfo(int levelNum, int peopleNum, int securityNum, int copNum, int emtNum, int backUpTime) {
        this.levelNum = levelNum;
        this.peopleNum = peopleNum;
        this.securityNum = securityNum;
        this.copNum = copNum;
        this.emtNum = emtNum;
        this.BackUpTime = backUpTime;
    }

    public WaveInfo(int levelNum, int peopleNum, int securityNum, int copNum, int emtNum, int backUpTime, int healthPackNum, int antidoes, int cureZones) {
        this.levelNum = levelNum;
        this.peopleNum = peopleNum;
        this.securityNum = securityNum;
        this.copNum = copNum;
        this.emtNum = emtNum;
        this.BackUpTime = backUpTime;
        this.healthPackNum = healthPackNum;
        this.antidoes = antidoes;
        this.cureZones = cureZones;
    }

    //not sure what else to add
    public int getBackUpTime() {
        return BackUpTime;
    }

    public int getLevelNum() {
        return levelNum;
    }

    public int getPeopleNum() {
        return peopleNum;
    }

    public int getSecurityNum() {
        return securityNum;
    }

    public int getCopNum() {
        return copNum;
    }

    public int getEmtNum() {
        return emtNum;
    }

    public int getHealthPackNum() {
        return healthPackNum;
    }

    public int getAntidoes() {
        return antidoes;
    }

    public int getCureZones() {
        return cureZones;
    }
}
