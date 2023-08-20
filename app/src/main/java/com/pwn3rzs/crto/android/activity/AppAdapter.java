package com.pwn3rzs.crto.android.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.pwn3rzs.crto.android.R;
import com.pwn3rzs.crto.android.activity.appitem.SimpleLine;
import com.pwn3rzs.crto.android.activity.appitem.AppViewHolder;
import com.pwn3rzs.crto.android.adapter.AdapterUtils;
import com.pwn3rzs.crto.android.adapter.ResType;
import com.pwn3rzs.crto.android.adapter.header.HeaderObject;
import com.pwn3rzs.crto.android.adapter.header.HeaderViewHolder;
import com.pwn3rzs.crto.android.adapter.headerCollapsed.HeaderCollapsedObject;
import com.pwn3rzs.crto.android.adapter.headerCollapsed.HeaderCollapsedVH;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import pokercc.android.expandablerecyclerview.ExpandableAdapter;


public class AppAdapter extends ExpandableAdapter<ExpandableAdapter.ViewHolder> {

    private static final int COLLAPSE_HEADER_ITEM = R.layout.item_group_header;
    private static final int EMPTY_VIEW = 102;

    private static final int TYPE_SIMPLE = 101;

    private final Context context;
    private List<ViewModel> data;

    public AppAdapter(Context context, String fileName) {
        this.context = context;
        try {
            loadJSONFromAsset(context, fileName);
        } catch (Exception e) {
            DLog.handleException(e);
        }
    }


    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        try {
            ViewModel app1 = data.get(position);
            if (app1 instanceof SimpleLine) {
                SimpleLine app = (SimpleLine) data.get(position);
                holder.bind(app, position);


                holder.itemView.setOnLongClickListener(v -> {
                    showPopupMenu(v);
                    return true;
                });
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void loadJSONFromAsset(Context context, String fileName) {
        try {
            AssetManager am = context.getAssets();
            data = new ArrayList<>();
            AdapterUtils.treeViewer(am, data, fileName, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void openGooglePlay(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean isIntentSafe = activities.size() > 0;
        if (isIntentSafe) {
            context.startActivity(intent);
        }
    }

    @SuppressLint("NonConstantResourceId")
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(context, view);
//        popupMenu.getMenuInflater().inflate(R.menu.menu_app_item, popupMenu.getMenu());
//
//        popupMenu.setOnMenuItemClickListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.menu_option1:
//                    // Обработка действия всплывающего меню
//                    return true;
//                case R.id.menu_option2:
//                    // Обработка действия всплывающего меню
//                    return true;
//                default:
//                    return false;
//            }
//        });

        popupMenu.show();
    }


    //@@@@@@@@@@@

    @NonNull
    @Override
    protected ViewHolder onCreateGroupViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v0;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_SIMPLE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app_other, parent, false);
            return new AppViewHolder(view);
        } else if (viewType == COLLAPSE_HEADER_ITEM) {
            v0 = inflater.inflate(R.layout.item_group_header, parent, false);
            return new HeaderCollapsedVH(v0);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app_other, parent, false);
            return new AppViewHolder(view);
        }
    }

    @NonNull
    @Override
    protected ViewHolder onCreateChildViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v0;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_SIMPLE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app_other, parent, false);
            return new AppViewHolder(view);
        } else if (viewType == COLLAPSE_HEADER_ITEM) {
            v0 = inflater.inflate(R.layout.item_group_header, parent, false);
            return new HeaderCollapsedVH(v0);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app_other, parent, false);
            return new AppViewHolder(view);
        }
    }

    @Override
    protected void onBindChildViewHolder(@NonNull ExpandableAdapter.ViewHolder holder, int groupPosition, int childPosition, List<?> payloads) {
        ViewModel group = data.get(groupPosition);
        if (group instanceof HeaderCollapsedObject) {

            if (payloads.isEmpty()) {
                ViewModel o = ((HeaderCollapsedObject) group).list.get(childPosition);
                if (o instanceof SimpleLine) {
                    //holder.itemView.setBackgroundColor(Color.RED);
                    SimpleLine app = (SimpleLine) o;
                    ((AppViewHolder) holder).bind(app, childPosition);
                    holder.itemView.setOnClickListener(v ->
                    {
                        if (app.resType == ResType.DIR) {
                            Toast.makeText(context, app.url, Toast.LENGTH_SHORT).show();
                        } else if (app.resType == ResType.FILE) {
                            Intent intent = new Intent(context, MarkdownActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("asset_file_name", app.fullPath0); // Замените на имя вашего файла
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }
                        //Module_U.openMarketApp(context, app.url);
                    });
                } else if (o instanceof HeaderObject) {
                    ((HeaderViewHolder) holder).bind((HeaderObject) o);
                }
            }
        }
    }


    @Override
    protected void onBindGroupViewHolder(@NonNull ViewHolder holder, int groupPosition, boolean expand, @NonNull List<?> payloads) {
        Object o = data.get(groupPosition);
        if (payloads.isEmpty()) {

            //Not collapsed
            if (o instanceof SimpleLine) {
                ((AppViewHolder) holder).bind((SimpleLine) o, groupPosition);
            } else if (o instanceof HeaderObject) {
                ((HeaderViewHolder) holder).bind((HeaderObject) o);
            } else if (o instanceof HeaderCollapsedObject) { //Collapsed
                HeaderCollapsedObject m = ((HeaderCollapsedObject) o);
                HeaderCollapsedVH h = ((HeaderCollapsedVH) holder);
                h.bind(m);
            }
        }
    }

    @Override
    public int getGroupItemViewType(int groupPosition) {
        if (data.size() == 0) {
            return EMPTY_VIEW;
        }
        Object model = data.get(groupPosition);
        if (model instanceof SimpleLine) {
            return TYPE_SIMPLE;
        }
//        else if (model instanceof HeaderObject) {
//            return TYPE_HEADER;
//        }
        else if (data.get(groupPosition) instanceof HeaderCollapsedObject) {
            return COLLAPSE_HEADER_ITEM;
        }
        return EMPTY_VIEW;
    }

    @Override
    protected void onGroupViewHolderExpandChange(@NonNull ViewHolder holder, int groupPosition, long animDuration, boolean expand) {

    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildCount(int groupPosition) {
        if (data.get(groupPosition) instanceof HeaderCollapsedObject) {
            return ((HeaderCollapsedObject) data.get(groupPosition)).list.size();
        }
        return 0;
    }
}