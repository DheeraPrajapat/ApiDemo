package com.example.apidemo.Team;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apidemo.Event.OwnEvent.OwnEventBody;
import com.example.apidemo.Event.OwnEvent.OwnSchoolEventBody;
import com.example.apidemo.Event.OwnEvent.OwnTeamEventBody;
import com.example.apidemo.R;

import java.util.List;

class OwnSchoolEventAdapter extends RecyclerView.Adapter<OwnSchoolEventAdapter.ViewHolder>
{
    Context context;
    List<OwnSchoolEventBody>  bodyList;
    public OwnSchoolEventAdapter(List<OwnSchoolEventBody>  bodyList, TeamActivity teamActivity) {
        this.bodyList=bodyList;
        this.context=teamActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.own_team_school_event,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getOwnEt_name.setText(bodyList.get(position).getTitle());
        holder.getGetOwnEt_description.setText(bodyList.get(position).getDescription());
        holder.getOwnGetEt_Type.setText(bodyList.get(position).getEvent_type());
        holder.getGetOwnEt_startTime.setText(bodyList.get(position).getStart_time());
        holder.getOwnGetGetEt_endTime.setText(bodyList.get(position).getEnd_time());
    }

    @Override
    public int getItemCount() {
       return bodyList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView getOwnEt_name,getGetOwnEt_description,getGetOwnEt_startTime,getOwnGetGetEt_endTime,getOwnGetEt_Type;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            getOwnEt_name=itemView.findViewById(R.id.getOwnEventName);
            getGetOwnEt_description=itemView.findViewById(R.id.getTeamEventDescription);
            getGetOwnEt_startTime=itemView.findViewById(R.id.getOwnEventStartTime);
            getOwnGetGetEt_endTime=itemView.findViewById(R.id.getOwnEventEndTime);
            getOwnGetEt_Type=itemView.findViewById(R.id.getOwnEventType);
        }
    }
}


class OwnTeamEventAdapter extends RecyclerView.Adapter<OwnTeamEventAdapter.ViewHolder>
{
    Context context;
    List<OwnTeamEventBody>  bodyList;
    public OwnTeamEventAdapter(List<OwnTeamEventBody>  bodyList, TeamActivity teamActivity) {
        this.bodyList=bodyList;
        this.context=teamActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.own_team_school_event,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getOwnEt_name.setText(bodyList.get(position).getTitle());
        holder.getGetOwnEt_description.setText(bodyList.get(position).getDescription());
        holder.getOwnGetEt_Type.setText(bodyList.get(position).getEvent_type());
        holder.getGetOwnEt_startTime.setText(bodyList.get(position).getStart_time());
        holder.getOwnGetGetEt_endTime.setText(bodyList.get(position).getEnd_time());
    }

    @Override
    public int getItemCount() {
        return bodyList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView getOwnEt_name,getGetOwnEt_description,getGetOwnEt_startTime,getOwnGetGetEt_endTime,getOwnGetEt_Type;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            getOwnEt_name=itemView.findViewById(R.id.getOwnEventName);
            getGetOwnEt_description=itemView.findViewById(R.id.getTeamEventDescription);
            getGetOwnEt_startTime=itemView.findViewById(R.id.getOwnEventStartTime);
            getOwnGetGetEt_endTime=itemView.findViewById(R.id.getOwnEventEndTime);
            getOwnGetEt_Type=itemView.findViewById(R.id.getOwnEventType);
        }
    }
}
