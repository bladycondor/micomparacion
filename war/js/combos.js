var martillo='';
var campo_id='';
var campito='';
function setBorrarCombo(id){
  var combo=document.getElementById(id);
  combo.length=1;
  combo.disabled=true;
}
function setCargarCombito(){
  if(request.readyState==4 && request.responseText!=''){
    var response=eval('('+request.responseText+')');
    setBorrarCombo(martillo);
    if(response.length>0){
      var combo=document.getElementById(martillo);
      for(var i=0;i<response.length;i++)
        eval('combo.options[combo.length]=new Option(response[i].'+campito+',response[i].'+campo_id+')');
      combo.disabled=false;
    }
  }
}