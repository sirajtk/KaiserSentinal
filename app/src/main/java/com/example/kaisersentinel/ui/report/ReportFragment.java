package com.example.kaisersentinel.ui.report;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.filter.Filter;
import com.evrencoskun.tableview.pagination.Pagination;
import com.example.kaisersentinel.PopUp.TableViewAdapter;
import com.example.kaisersentinel.PopUp.TableViewListener;
import com.example.kaisersentinel.PopUp.TableViewModel;
import com.example.kaisersentinel.R;

public class ReportFragment extends Fragment {

//    private ReportViewModel reportViewModel;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        reportViewModel =
//                ViewModelProviders.of(this).get(ReportViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_report, container, false);
//        final TextView textView = root.findViewById(R.id.text_report);
//        reportViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        return root;
//    }


    private Spinner moodFilter, genderFilter;
    private ImageButton previousButton, nextButton;
    private TextView tablePaginationDetails;
    private TableView mTableView;
    private Filter mTableFilter; // This is used for filtering the table.
    private Pagination mPagination; // This is used for paginating the table.

    private boolean mPaginationEnabled = false;

    public ReportFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setFullScreenMode();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.fragment_main, container, false);

        EditText searchField = layout.findViewById(R.id.query_string);
        searchField.addTextChangedListener(mSearchTextWatcher);

        moodFilter = layout.findViewById(R.id.mood_spinner);
        moodFilter.setOnItemSelectedListener(mItemSelectionListener);

        genderFilter = layout.findViewById(R.id.gender_spinner);
        genderFilter.setOnItemSelectedListener(mItemSelectionListener);

        Spinner itemsPerPage = layout.findViewById(R.id.items_per_page_spinner);

        View tableTestContainer = layout.findViewById(R.id.table_test_container);

        previousButton = layout.findViewById(R.id.previous_button);
        nextButton = layout.findViewById(R.id.next_button);
        EditText pageNumberField = layout.findViewById(R.id.page_number_text);
        tablePaginationDetails = layout.findViewById(R.id.table_details);

        if (mPaginationEnabled) {
            tableTestContainer.setVisibility(View.VISIBLE);
            itemsPerPage.setOnItemSelectedListener(onItemsPerPageSelectedListener);

            previousButton.setOnClickListener(mClickListener);
            nextButton.setOnClickListener(mClickListener);
            pageNumberField.addTextChangedListener(onPageTextChanged);
        } else {
            tableTestContainer.setVisibility(View.GONE);
        }

        // Let's get TableView
        mTableView =  layout.findViewById(R.id.tableview);

        initializeTableView();

        if (mPaginationEnabled) {
            mTableFilter = new Filter(mTableView); // Create an instance of a Filter and pass the
            // created TableView.

            // Create an instance for the TableView pagination and pass the created TableView.
            mPagination = new Pagination(mTableView);

            // Sets the pagination listener of the TableView pagination to handle
            // pagination actions. See onTableViewPageTurnedListener variable declaration below.
            mPagination.setOnTableViewPageTurnedListener(onTableViewPageTurnedListener);
        }
        return layout;
    }

    private void initializeTableView() {
        // Create TableView View model class  to group view models of TableView
        TableViewModel tableViewModel = new TableViewModel(getContext());

        // Create TableView Adapter
        TableViewAdapter tableViewAdapter = new TableViewAdapter(requireContext(), tableViewModel);

        mTableView.setAdapter(tableViewAdapter);
        mTableView.setTableViewListener(new TableViewListener(mTableView));

        // Create an instance of a Filter and pass the TableView.
        //mTableFilter = new Filter(mTableView);

        // Load the dummy data to the TableView
        tableViewAdapter.setAllItems(tableViewModel.getColumnHeaderListreport(), tableViewModel
                .getRowHeaderListreport(), tableViewModel.getCellListreport(getContext()));

        //mTableView.setHasFixedWidth(true);

        /*for (int i = 0; i < mTableViewModel.getCellList().size(); i++) {
            mTableView.setColumnWidth(i, 200);
        }*)
        //mTableView.setColumnWidth(0, -2);
        //mTableView.setColumnWidth(1, -2);
        /*mTableView.setColumnWidth(2, 200);
        mTableView.setColumnWidth(3, 300);
        mTableView.setColumnWidth(4, 400);
        mTableView.setColumnWidth(5, 500);*/

    }

    public void filterTable(@NonNull String filter) {
        // Sets a filter to the table, this will filter ALL the columns.
        mTableFilter.set(filter);
    }

    public void filterTableForMood(@NonNull String filter) {
        // Sets a filter to the table, this will only filter a specific column.
        // In the example data, this will filter the mood column.
        mTableFilter.set(TableViewModel.MOOD_COLUMN_INDEX, filter);
    }

    public void filterTableForGender(@NonNull String filter) {
        // Sets a filter to the table, this will only filter a specific column.
        // In the example data, this will filter the gender column.
        mTableFilter.set(TableViewModel.GENDER_COLUMN_INDEX, filter);
    }

    // The following four methods below: nextTablePage(), previousTablePage(),
    // goToTablePage(int page) and setTableItemsPerPage(int itemsPerPage)
    // are for controlling the TableView pagination.
    public void nextTablePage() {
        mPagination.nextPage();
    }

    public void previousTablePage() {
        mPagination.previousPage();
    }

    public void goToTablePage(int page) {
        mPagination.goToPage(page);
    }

    public void setTableItemsPerPage(int itemsPerPage) {
        mPagination.setItemsPerPage(itemsPerPage);
    }

    // Handler for the changing of pages in the paginated TableView.
    @NonNull
    private Pagination.OnTableViewPageTurnedListener onTableViewPageTurnedListener = new
            Pagination.OnTableViewPageTurnedListener() {
                @Override
                public void onPageTurned(int numItems, int itemsStart, int itemsEnd) {
                    int currentPage = mPagination.getCurrentPage();
                    int pageCount = mPagination.getPageCount();
                    previousButton.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.VISIBLE);

                    if (currentPage == 1 && pageCount == 1) {
                        previousButton.setVisibility(View.INVISIBLE);
                        nextButton.setVisibility(View.INVISIBLE);
                    }

                    if (currentPage == 1) {
                        previousButton.setVisibility(View.INVISIBLE);
                    }

                    if (currentPage == pageCount) {
                        nextButton.setVisibility(View.INVISIBLE);
                    }

                    tablePaginationDetails.setText(getString(R.string.table_pagination_details, String
                            .valueOf(currentPage), String.valueOf(itemsStart), String.valueOf(itemsEnd)));

                }
            };

    @NonNull
    private AdapterView.OnItemSelectedListener mItemSelectionListener = new AdapterView
            .OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // 0. index is for empty item of spinner.
            if (position > 0) {

                String filter = Integer.toString(position);

                if (parent == moodFilter) {
                    filterTableForMood(filter);
                } else if (parent == genderFilter) {
                    filterTableForGender(filter);
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // Left empty intentionally.
        }
    };

    @NonNull
    private TextWatcher mSearchTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            filterTable(String.valueOf(s));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @NonNull
    private AdapterView.OnItemSelectedListener onItemsPerPageSelectedListener = new AdapterView
            .OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int itemsPerPage;
            if ("All".equals(parent.getItemAtPosition(position).toString())) {
                itemsPerPage = 0;
            } else {
                itemsPerPage = Integer.valueOf(parent.getItemAtPosition(position).toString());
            }

            setTableItemsPerPage(itemsPerPage);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @NonNull
    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == previousButton) {
                previousTablePage();
            } else if (v == nextButton) {
                nextTablePage();
            }
        }
    };

    @NonNull
    private TextWatcher onPageTextChanged = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int page;
            if (TextUtils.isEmpty(s)) {
                page = 1;
            } else {
                page = Integer.valueOf(String.valueOf(s));
            }

            goToTablePage(page);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}