function getXmlHttpRequest(){
  if(window.XMLHttpRequest){
    return new XMLHttpRequest();
  }else if(window.ActiveXObject){
    return new ActiveXObject("Microsoft.XMLHTTP");
  }else{
    alert("No se pudo crear el Objeto XmlHttpRequest. Considere actualizar su navegador.");
  }
  return false;
}
var request=getXmlHttpRequest();
var param=null;
function getConexion(url,funcion){
  if(request.readyState==4 || request.readyState==0){
    request.open('POST',url,true);
    request.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
    eval("request.onreadystatechange="+funcion);
    request.send(param);
    param=null;
  }
}