package com.pwn3rzs.crto.android.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pwn3rzs.crto.android.R;

public class AppListFragment extends Fragment {

    private static final String KEY_VAR0 = AppListFragment.class.getSimpleName();
    private String fileName;

    public static Fragment newInstance(String s) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_VAR0, s);
        AppListFragment fragment = new AppListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fileName = getArguments().getString(KEY_VAR0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.expandableRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        if (savedInstanceState == null) {
            AppAdapter configAdapter = new AppAdapter(getActivity(), fileName);
            recyclerView.setAdapter(configAdapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}