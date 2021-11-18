package com.example.apidemo.Event.OwnEvent;

import java.util.List;

public class OwnEventBody
{
    public List<OwnSchoolEventBody> school_event;
    public List<OwnTeamEventBody> team_event;

    public List<OwnSchoolEventBody> getSchool_event() {
        return school_event;
    }

    public void setSchool_event(List<OwnSchoolEventBody> school_event) {
        this.school_event = school_event;
    }

    public List<OwnTeamEventBody> getTeam_event() {
        return team_event;
    }

    public void setTeam_event(List<OwnTeamEventBody> team_event) {
        this.team_event = team_event;
    }
}
