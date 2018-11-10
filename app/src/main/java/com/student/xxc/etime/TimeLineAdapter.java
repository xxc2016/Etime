package com.student.xxc.etime;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.student.xxc.etime.entity.Trace;
import com.student.xxc.etime.impl.SetTraceActivity;
import com.student.xxc.etime.impl.TraceManager;

import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.ViewHolder> {

    private Context context;
    private List<Trace>traces;
    private int downX;
    private int upX;

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView time, event;//时间、事件
        private TextView tvDot;//图标
        public LinearLayout del;//删除按钮
        public LinearLayout activity;//活动部分
        public LinearLayout finish;//完成按钮
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time=(TextView)itemView.findViewById(R.id.tvAcceptTime);
            event=(TextView)itemView.findViewById(R.id.tvAcceptStation);
            tvDot=(TextView)itemView.findViewById(R.id.tvDot);
            del = (LinearLayout)itemView.findViewById(R.id.del);
            activity=(LinearLayout)itemView.findViewById(R.id.activity);
            finish=(LinearLayout)itemView.findViewById(R.id.finish);
        }
        public float getActionWidth(){
            return del.getWidth();
        }
    }

    TimeLineAdapter(Context text,List<Trace> races) {
        traces=races;
        context=text;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.time_line,viewGroup,false);
        return new ViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
//        if (getItemViewType(position) == TYPE_TOP) {
//            // 第一行头的竖线不显示
//            viewHolder.tvTopLine.setVisibility(View.INVISIBLE);
//            // 字体颜色加深
//
//        } else if (getItemViewType(position) == TYPE_NORMAL) {
//            viewHolder.tvTopLine.setVisibility(View.VISIBLE);
//            viewHolder.time.setTextColor(0xff999999);
//            viewHolder.event.setTextColor(0xff999999);
//            viewHolder.tvDot.setBackgroundResource(R.drawable.ic_menu_send);
//        }
        viewHolder.time.setTextColor(0xff555555);
        viewHolder.event.setTextColor(0xff555555);
        viewHolder.tvDot.setBackgroundResource(R.drawable.ic_menu_send);

        viewHolder.time.setText(traces.get(position).getTime());
        viewHolder.event.setText(traces.get(position).getEvent());
        viewHolder.itemView.findViewById(R.id.card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,viewHolder.getAdapterPosition()+"",Toast.LENGTH_SHORT).show();//切入Activity
                Intent intent =new Intent();
                intent.putExtra("traceId",traces.get(viewHolder.getAdapterPosition()).getTraceId());
                intent.putExtra("time",traces.get(viewHolder.getAdapterPosition()).getTime());
                intent.putExtra("event",traces.get(viewHolder.getAdapterPosition()).getEvent());
                intent.setClass(context, SetTraceActivity.class);
                startActivityForResult((MainActivity)context,intent,2,null);
            }
        });
        viewHolder.itemView.findViewById(R.id.card).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {//长按事件
                DragItemTouchHelper.getHelper().startDrag(viewHolder);
                return true;
            }
        });
        viewHolder.itemView.findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeData(viewHolder.getAdapterPosition());
                DragItemTouchHelper.ifdel=true;
            }
        });
        viewHolder.itemView.findViewById(R.id.finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishData(viewHolder.getAdapterPosition());
                DragItemTouchHelper.ifdel=true;
            }
        });
    }
    @Override
    public int getItemCount() {
        return traces.size();
    }

    private void finishData(int position) {
        TraceManager.finishTrace(traces.get(position));
        traces.remove(position);
        notifyItemRemoved(position);
    }

    public void addData(Trace one, int position)
    {
          traces.add(position, one);
          TraceManager.addTrace(one); //输入数据库
          notifyItemInserted(position);
     }

    public void removeData(int position) {
        TraceManager.deleteTrace(traces.get(position));
        traces.remove(position);
        notifyItemRemoved(position);

    }
    public void MoveToPosition(LinearLayoutManager manager, int n) {
        int m=manager.findFirstVisibleItemPosition();
        if(n<=m) {
            manager.scrollToPositionWithOffset(n, 0);
//            manager.setStackFromEnd(true);
        }
    }
}
