package rina.util;


/**
 * @author Rina!
 *
 * Created by Rina in 27/07/2020.
 *
 **/
public enum TurokGUIState {
	// Frame.
	GUI_FRAME_OPENING, GUI_FRAME_CLOSING,
	GUI_FRAME_OPEN,    GUI_FRAME_CLOSED,
	GUI_FRAME_MOVING,  GUI_FRAME_STOPPED,

	// Menu:mbutton.
	GUI_MBUTTON_MENU_OPENING, GUI_MBUTTON_MENU_CLOSING,
	GUI_MBUTTON_MENU_OPEN,    GUI_MBUTTON_MENU_CLOSED;
}