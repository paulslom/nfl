function autotab(currentElement)
{
	//alert('i am inside autotab.  current element value length: ' + currentElement.value.length);
	
	var nextTabIndex=999;
		
    if (currentElement.getAttribute && currentElement.value.length == currentElement.getAttribute("size")) 
    {
		//alert('reached max length so i am tabbing');
		
       	var inputTabIndex=currentElement.tabIndex;
	
		const allElements = document.querySelectorAll('input');

		// Loop through each element
		for (const element of allElements) 
		{
		 	if (element.type != "hidden"
		 	&&  element.tabIndex > inputTabIndex
			&&  element.tabIndex < nextTabIndex )
		 	{					      
		     	//alert("element tagName = " + element.tagName); 
		     	//alert("element type = " + element.type)
		     	//alert("element tab index = " + element.tabIndex);
		     	
		     	element.select();
				element.focus();     	
		     		     
		        //alert("***changed!***");
		        break;
		     }
		 }
		
	}
	
	
}