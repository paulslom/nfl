function elmName()
{
   for(i=0; i<document.forms[0].elements.length; i++)
   {
   	  alert("name = " + document.forms[0].elements[i].name + " index= " + i)
   }
}