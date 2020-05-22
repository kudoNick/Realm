package com.example.realm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class UserAdapter extends BaseAdapter {
    Context context;
    List<User> userList;

    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.data, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

            User user = (User) getItem(position);

            final int numposition = user.getUser_id();
            viewHolder.tvName.setText(user.getName());
//            viewHolder.tvID.setText(user.getUser_id());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,UpdateActivity.class);
                    intent.putExtra("numPosition",numposition);
                    context.startActivity(intent);
                }
            });
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    private class ViewHolder {
        TextView tvName,tvID;


        public ViewHolder(View view) {
            tvName = (TextView)view.findViewById(R.id.tvName);
        }
    }


}
