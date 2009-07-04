c_styles={};c_menus={}; // do not remove this line

c_hideTimeout=100; // 1000==1 second (500)
c_subShowTimeout=50; // (250)
c_keepHighlighted=true;
c_findCURRENT=false; // find the item linking to the current page and apply it the CURRENT style class
c_findCURRENTTree=true;
c_overlapControlsInIE=false;
c_rightToLeft=false; // if the menu text should have "rtl" direction (e.g. Hebrew, Arabic)

c_imagesPath="/include/header/images/"; // path to the directory containing the menu images

c_styles['MM']=[ // MainMenu (the shorter the class name the better)
[
// MENU BOX STYLE
0,		// BorderWidth
'solid',	// BorderStyle (CSS valid values except 'none')
'#8F90C4',	// BorderColor ('color')
0,		// Padding
'#CBCBEF',	// Background ('color','transparent','[image_source]')
'',		// IEfilter (only transition filters work well - not static filters)
''		// Custom additional CSS for the menu box (valid CSS)
],[
// MENU ITEMS STYLE
0,		// BorderWidth
'solid',	// BorderStyle (CSS valid values except 'none')
'solid',	// OVER BorderStyle
'#FFF5C6',	// BorderColor ('color')
'#4d4c76',	// OVER BorderColor
6,		// Padding
'[navigation_bg.gif]',	// Background ('color','transparent','[image_source]')
'[navigation_bg_leftline.gif]',	// OVER Background
'#FFFFFF',	// Color
'#FFFFFF',	// OVER Color
'10px',		// FontSize (values in CSS valid units - %,em,ex,px,pt)
'verdana,arial,helvetica,sans-serif',	// FontFamily
'bold',		// FontWeight (CSS valid values - 'bold','normal','bolder','lighter','100',...,'900')
'none',		// TextDecoration (CSS valid values - 'none','underline','overline','line-through')
'none',		// OVER TextDecoration
'center',		// TextAlign ('left','center','right','justify')
1,		// ItemsSeparatorSize
'solid',	// ItemsSeparatorStyle (border-style valid values)
'#336699',	// ItemsSeparatorColor ('color','transparent')
0,		// ItemsSeparatorSpacing
false,			// UseSubMenuImage (true,false)
'',	// SubMenuImageSource ('[image_source]')
'',	// OverSubMenuImageSource
7,			// SubMenuImageWidth
4,			// SubMenuImageHeight
'10',			// SubMenuImageVAlign ('pixels from item top','middle')
'solid',		// VISITED BorderStyle
'#FFF5C6',		// VISITED BorderColor
'[navigation_bg.gif]',		// VISITED Background
'#FFFFFF',		// VISITED Color
'none',			// VISITED TextDecoration
'[h_arrow.gif]',	// VISITED SubMenuImageSource
'solid',		// CURRENT BorderStyle
'#CBCBEF',		// CURRENT BorderColor
'[navigation_bg.gif]',		// CURRENT Background
'#FFFFFF',		// CURRENT Color
'none',			// CURRENT TextDecoration
'[h_arrow.gif]',	// CURRENT SubMenuImageSource
'',		// Custom additional CSS for the items (valid CSS)
'',		// OVER Custom additional CSS for the items (valid CSS)
'',		// CURRENT Custom additional CSS for the items (valid CSS)
''		// VISITED Custom additional CSS for the items (valid CSS)
]];


c_styles['SM']=[ // SubMenus
[
// MENU BOX STYLE
1,		// BorderWidth
'solid',	// BorderStyle (CSS valid values except 'none')
'#000033',	// BorderColor ('color')
10,		// Padding
'#336699',	// Background ('color','transparent','[image_source]')
'',		// IEfilter (only transition filters work well - not static filters)
'border-top:none'		// Custom additional CSS for the menu box (valid CSS)
],[
// MENU ITEMS STYLE
0,		// BorderWidth
'',	// BorderStyle (CSS valid values except 'none')
'',	// OVER BorderStyle
'',	// BorderColor ('color')
'',	// OVER BorderColor
1,		// Padding
'#336699',	// Background ('color','transparent','[image_source]')
'#336699',	// OVER Background
'#ff9900',	// Color
'#000000',	// OVER Color
'10px',		// FontSize (values in CSS valid units - %,em,ex,px,pt)
'verdana,arial,helvetica,sans-serif',	// FontFamily
'bold',	// FontWeight (CSS valid values - 'bold','normal','bolder','lighter','100',...,'900')
'none',		// TextDecoration (CSS valid values - 'none','underline','overline','line-through')
'none',		// OVER TextDecoration
'left',		// TextAlign ('left','center','right','justify')
0,		// ItemsSeparatorSize
'solid',	// ItemsSeparatorStyle (border-style valid values)
'#336699',	// ItemsSeparatorColor ('color','transparent')
5,		// ItemsSeparatorSpacing
true,			// UseSubMenuImage (true,false)
'[v_arrow.gif]',	// SubMenuImageSource ('[image_source]')
'[v_arrow_over.gif]',	// OverSubMenuImageSource
7,			// SubMenuImageWidth
7,			// SubMenuImageHeight
'6',			// SubMenuImageVAlign ('pixels from item top','middle')
'solid',		// VISITED BorderStyle
'#FFF5C6',		// VISITED BorderColor
'#336699',		// VISITED Background
'#ff9900',		// VISITED Color
'none',			// VISITED TextDecoration
'[v_arrow.gif]',	// VISITED SubMenuImageSource
'solid',		// CURRENT BorderStyle
'#CBCBEF',		// CURRENT BorderColor
'#336699',		// CURRENT Background
'#ff9900',		// CURRENT Color
'none',			// CURRENT TextDecoration
'[v_arrow.gif]',	// CURRENT SubMenuImageSource
'',		// Custom additional CSS for the items (valid CSS)
'',		// OVER Custom additional CSS for the items (valid CSS)
'',		// CURRENT Custom additional CSS for the items (valid CSS)
''		// VISITED Custom additional CSS for the items (valid CSS)
]];

c_styles['SM1']=[ // SubMenus
[
// MENU BOX STYLE
1,		// BorderWidth
'solid',	// BorderStyle (CSS valid values except 'none')
'#000033',	// BorderColor ('color')
10,		// Padding
'#336699',	// Background ('color','transparent','[image_source]')
'',		// IEfilter (only transition filters work well - not static filters)
''		// Custom additional CSS for the menu box (valid CSS)
],[
// MENU ITEMS STYLE
0,		// BorderWidth
'',	// BorderStyle (CSS valid values except 'none')
'',	// OVER BorderStyle
'',	// BorderColor ('color')
'',	// OVER BorderColor
1,		// Padding
'#336699',	// Background ('color','transparent','[image_source]')
'#336699',	// OVER Background
'#ff9900',	// Color
'#000000',	// OVER Color
'10px',		// FontSize (values in CSS valid units - %,em,ex,px,pt)
'verdana,arial,helvetica,sans-serif',	// FontFamily
'bold',	// FontWeight (CSS valid values - 'bold','normal','bolder','lighter','100',...,'900')
'none',		// TextDecoration (CSS valid values - 'none','underline','overline','line-through')
'none',		// OVER TextDecoration
'left',		// TextAlign ('left','center','right','justify')
0,		// ItemsSeparatorSize
'solid',	// ItemsSeparatorStyle (border-style valid values)
'#336699',	// ItemsSeparatorColor ('color','transparent')
5,		// ItemsSeparatorSpacing
true,			// UseSubMenuImage (true,false)
'[v_arrow.gif]',	// SubMenuImageSource ('[image_source]')
'[v_arrow_over.gif]',	// OverSubMenuImageSource
7,			// SubMenuImageWidth
7,			// SubMenuImageHeight
'6',			// SubMenuImageVAlign ('pixels from item top','middle')
'solid',		// VISITED BorderStyle
'#FFF5C6',		// VISITED BorderColor
'#336699',		// VISITED Background
'#ff9900',		// VISITED Color
'none',			// VISITED TextDecoration
'[v_arrow.gif]',	// VISITED SubMenuImageSource
'solid',		// CURRENT BorderStyle
'#CBCBEF',		// CURRENT BorderColor
'#336699',		// CURRENT Background
'#ff9900',		// CURRENT Color
'none',			// CURRENT TextDecoration
'[v_arrow.gif]',	// CURRENT SubMenuImageSource
'',		// Custom additional CSS for the items (valid CSS)
'',		// OVER Custom additional CSS for the items (valid CSS)
'',		// CURRENT Custom additional CSS for the items (valid CSS)
''		// VISITED Custom additional CSS for the items (valid CSS)
]];

c_menus['Menu1']=[ // the UL element with id="Menu1"
[
// MAIN-MENU FEATURES
'horizontal',	// ItemsArrangement ('vertical','horizontal')
'relative',	// Position ('relative','absolute','fixed')
'0',		// X Position (values in CSS valid units- px,em,ex)
'0',		// Y Position (values in CSS valid units- px,em,ex)
false,		// RightToLeft display of the sub menus
false,		// BottomToTop display of the sub menus
0,		// X SubMenuOffset (pixels)
0,		// Y SubMenuOffset
'10em',		// Width (values in CSS valid units - px,em,ex) (matters for main menu with 'vertical' ItemsArrangement only)
'MM',		// CSS Class (one of the defined in section 2)
false		// Open sub-menus onclick (default is onmouseover)
],[
// SUB-MENUS FEATURES
0,		// X SubMenuOffset (pixels)
-10,		// Y SubMenuOffset
'auto',		// Width ('auto',values in CSS valid units - px,em,ex)
'175',		// MinWidth ('pixels') (matters/useful if Width is set 'auto')
'300',		// MaxWidth ('pixels') (matters/useful if Width is set 'auto')
'SM',		// CSS Class (one of the defined in section 2)
false		// Open sub-menus onclick (default is onmouseover)
],[
// SUB-MENUS FEATURES
0,		// X SubMenuOffset (pixels)
0,		// Y SubMenuOffset
'auto',		// Width ('auto',values in CSS valid units - px,em,ex)
'175',		// MinWidth ('pixels') (matters/useful if Width is set 'auto')
'300',		// MaxWidth ('pixels') (matters/useful if Width is set 'auto')
'SM1',		// CSS Class (one of the defined in section 2)
false		// Open sub-menus onclick (default is onmouseover)
]];