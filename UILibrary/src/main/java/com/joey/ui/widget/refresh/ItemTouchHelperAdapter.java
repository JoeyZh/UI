package com.joey.ui.widget.refresh;

public interface ItemTouchHelperAdapter {

    /**
     * Called when an item has been dragged far enough to trigger a move. This is called every time
     * an item is shifted, and <strong>not</strong> at the end of a "drop" event.<br/>
     * <br/>

//     * @see RecyclerView.ViewHolder#getAdapterPosition()
     */
    void onItemMove(int fromPosition, int toPosition);


    /**
     * Called when an item has been dismissed by a swipe.<br/>
     * <br/>
//
//     * adjusting the underlying data to reflect this removal.
//     *
//     * @param position The position of the item dismissed.
//     * @see RecyclerView#getAdapterPositionFor(RecyclerView.ViewHolder)
//     * @see RecyclerView.ViewHolder#getAdapterPosition()
     */
    void onItemDismiss(int position);
}
