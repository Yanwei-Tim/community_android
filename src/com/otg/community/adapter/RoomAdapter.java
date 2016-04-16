package com.otg.community.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.otg.community.R;
import com.otg.community.activities.LardLordInfoActivity;
import com.otg.community.activities.TenantInfoActivity;
import com.otg.community.domain.LardLord;
import com.otg.community.domain.Tenant;

import java.util.List;

public class RoomAdapter extends BaseAdapter {
    List<Object> objectList;
    private List<String> listTag;
    LayoutInflater inflater;
    Context context;

    public RoomAdapter(Context context, List<Object> objectList, List<String> listTag) {
        this.context = context;
        this.objectList = objectList;
        this.listTag = listTag;
        this.inflater = LayoutInflater.from(context);
    }

    public void onDateChange(List<Object> objectList, List<String> listTag) {
        this.objectList = objectList;
        this.listTag = listTag;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return objectList.size();
    }

    @Override
    public Object getItem(int position) {
        return objectList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean isEnabled(int position) {
        if (listTag.contains(getItem(position))) {
            return false;
        }
        return super.isEnabled(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Object object = objectList.get(position);
            if (object instanceof String && listTag.contains(getItem(position))) {
                String ss = object.toString();
                 if("租客信息:".equals(ss)) {
                    convertView = inflater.inflate(R.layout.group_list_item_tag, null);
                    TextView tagText_tv = (TextView)convertView.findViewById(R.id.tag_text);
                    tagText_tv.setText(ss);
                }
            } else {
               if (object instanceof Tenant) {
                    final Tenant entity = (Tenant) object;
                    convertView = inflater.inflate(R.layout.listview_item_tenant_info, null);
                    TextView idCard_tv = (TextView) convertView.findViewById(R.id.idCard);
                    TextView name_tv = (TextView) convertView.findViewById(R.id.name);
                    TextView id_tv = (TextView) convertView.findViewById(R.id.id);
                    TextView room_tv = (TextView) convertView.findViewById(R.id.room);
                    ImageButton info_bt = (ImageButton) convertView.findViewById(R.id.info);
                    info_bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(context, TenantInfoActivity.class);
                            i.putExtra("tenant", entity);
                            context.startActivity(i);
                        }
                    });
                    idCard_tv.setText(entity.getIdCard());
                    id_tv.setText(String.valueOf(entity.getId()));
                    name_tv.setText(entity.getName());
                    room_tv.setText(String.valueOf(entity.getRoom()));
                }
            }
        return convertView;
    }
}
