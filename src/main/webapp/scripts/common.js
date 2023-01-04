var isNN = (navigator.appName.indexOf("Netscape")!=-1);

function destroySession()
{
	if ((window.event.clientX<0) && (window.event.clientY<0))
	{
	    //alert("reached the inside of destroySession");
		newWindow = window.open("InvalidateSessionAction.do",null,"height=10, width=10");
		newWindow.close();
	}
}

//function destroySession()
//{
	//alert("reached the inside of destroySession");

	// Get the page name
//    var sPath = window.location.pathname;    
//    var sPage = sPath.substring(sPath.lastIndexOf('/') + 1);
       
//    if (sPage == '')
//    {
//      sPage = "displayLoginForm.do";
//	}
	
    // They're in a  popup window, don't kill the session here!
//    if (window.opener != null)
//       return;

	// They're logging in so there's no session yet. Don't kill the session here!
//    if (sPage == 'displayLoginForm.do')
//       return;
 
    // Only want to run the endsession for IE, no way to tell currently if it's not IE and
    // the window is closing
    
//    if(window.event != null)
//    {
//       var abssize = document.body.offsetWidth - 30; 
       
//       if (window.event.clientY < 0 && event.clientX >= abssize)
//	   {
		  //alert("will attempt to do InvalidateSession action");
//		  window.location = "/Portfolio/InvalidateSessionAction.do";
		    		
//	   }
//	}   
//}

function IsNumeric(strString)
//  check for valid numeric strings	
{
  var strValidChars = "0123456789.-";
  var strChar;
  var blnResult = true;

  if (strString.length == 0) return false;

  //  test strString consists of valid characters listed above
  for (i = 0; i < strString.length && blnResult == true; i++)
  {
     strChar = strString.charAt(i);
     if (strValidChars.indexOf(strChar) == -1)
     {
        blnResult = false;
     }
  }
  return blnResult;
}

function round_decimals(original_number, decimals)
{
    var result1 = original_number * Math.pow(10, decimals)
    var result2 = Math.round(result1)
    var result3 = result2 / Math.pow(10, decimals)
    return pad_with_zeros(result3, decimals)
}

function pad_with_zeros(rounded_value, decimal_places)
{
    // Convert the number to a string
    var value_string = rounded_value.toString()
    
    // Locate the decimal point
    var decimal_location = value_string.indexOf(".")

    // Is there a decimal point?
    if (decimal_location == -1)
    {
        
        // If no, then all decimal places will be padded with 0s
        decimal_part_length = 0
        
        // If decimal_places is greater than zero, tack on a decimal point
        value_string += decimal_places > 0 ? "." : ""
    }
    else
    {
        // If yes, then only the extra decimal places will be padded with 0s
        decimal_part_length = value_string.length - decimal_location - 1
    }
    
    // Calculate the number of decimal places that need to be padded with 0s
    var pad_total = decimal_places - decimal_part_length
    
    if (pad_total > 0)
    {        
        // Pad the string with 0s
        for (var counter = 1; counter <= pad_total; counter++) 
            value_string += "0"
    }
    return value_string
}

function autoTab(input,len, e)
{
	var keyCode = (isNN) ? e.which : e.keyCode; 
	var filter = (isNN) ? [0,8,9] : [0,8,9,16,17,18,37,38,39,40,46];	
	if(input.value.length >= len && !containsElement(filter,keyCode))
	{
		input.value = input.value.slice(0, len);
		var nextTab = getNextTabIndex(input);
		if (input.form[nextTab].type != 'button' 
		&&  input.form[nextTab].type != 'select-one'
		&&  input.form[nextTab].type != 'submit')
		{
			//alert("type of element for .select is = " + input.form[nextTab].type);
			//alert("about to do .select on tabindex = " + nextTab);
			input.form[nextTab].select();
			//alert("after .select on tabindex = " + nextTab);
		}
		
		input.form[nextTab].focus();
		
	}
	
	function containsElement(arr, ele)
	{
		var found = false, index = 0;
		while(!found && (index < arr.length)){
			if(arr[index]== ele){
				found=true; 
			}else{
				index++;
			}
		}
		return found;
	}
	
	function getIndex(input) {
		var index=-1, i=0, found=false; 
		while (i < input.form.length && index== -1){
			if (input.form[i]== input){
				index=i; 
			}else{
				i++;
			}
		}
		return index;
	}
	
	function getNextTabIndex(input)
	{
		var index=999, i=0, found=false; 
		var inputTabIndex=input.tabIndex
		var firstTabIndex=0;
		var nextTabIndex=999;
		
		//debugger;
		
		for ( i = 0; i < document.forms[0].elements.length; i++ )
		{
		    //alert("i = " + i);
		    //alert("element name = " + document.forms[0].elements[i].name);
		    
			if (document.forms[0].elements[i].tagName == "INPUT"
			||  document.forms[0].elements[i].tagName == "SELECT")
			{
			   if (document.forms[0].elements[i].type != "hidden"
			   &&  document.forms[0].elements[i].tabIndex > inputTabIndex
			   &&  document.forms[0].elements[i].tabIndex < nextTabIndex )
			   {					      
			      //alert("element tagName = " + document.forms[0].elements[i].tagName); 
			      //alert("element type = " + document.forms[0].elements[i].type)
			      //alert("element tab index = " + document.forms[0].elements[i].tabIndex);
			      nextTabIndex = document.forms[0].elements[i].tabIndex;
			      index=i; 
			      //alert("index set to = " + index);
			   }
			   else
			   {
			      if (document.forms[0].elements[i].tabIndex == 1)
				  {
					 firstTabIndex = i;
				  }			
			   }
			}   
			 
		}
		
		if (index==999) //means the input was the highest tabIndex value; loop back to first
		{	
			index = firstTabIndex;
		}
		return index;
	}
	
	return true;
}


