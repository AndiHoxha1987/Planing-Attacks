# Planing-Attacks
Android, Java, MVVM, ViewModel, LiveData, RecycleViewAdapter,

This is a helper app for an online strategy game. Players can make fast calculations about walking time and landing time. I used the MVVM design pattern for this app. To store data are used LiveData, Room, and they are shown in a RecycleViewAdapter. The entire row is deleted by Swiping it. By clicking in an element of raw it changes the color of the element for a better view.  The entire data is possible to be deleted only in the menu section to avoid deleting by mistake.
The UI is accessible for all types of phones and tablets. Colors are based on the game colors, respectively enemies and their own cities. Two activities have different landscape layout, for a better UX. 

Features implemented are:
- MVVM design pattern
- ViewModel
- Livedata
- Room
- Dao 
- Repository
- RecycleView, Adapter
- RecycleView ItemTouchHelper(Swipe to delete an item)
- AsyncTask
- SharedPreferences
- MobileAds
