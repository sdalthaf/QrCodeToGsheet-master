package alabs.qsg;

public class AppScriptDummy {


    /**
     *
     *
     * function doGet(e){
     *
     *   var ss = SpreadsheetApp.openByUrl('https://docs.google.com/spreadsheets/d/1cvJPV3Zm-PwofOTOXkXhkxabGf8Y3D9pZcoRTcH3QoQ/edit#gid=0');
     *   var sheet = ss.getSheetByName("Sheet1");
     *
     *   return insert(e, sheet);
     * }
     *
     * function doPost(e) {
     * var ss = SpreadsheetApp.openByUrl('https://docs.google.com/spreadsheets/d/1cvJPV3Zm-PwofOTOXkXhkxabGf8Y3D9pZcoRTcH3QoQ/edit#gid=0');
     *   var sheet = ss.getSheetByName("Sheet1");
     *
     *   return insert(e, sheet);
     * }
     *
     * function insert(e,sheet) {
     *   var scannedData = e.parameter.sdata;
     *   var sdate = e.parameter.sdate;
     *   var stime = e.parameter.stime;
     *   var status = e.parameter.status;
     *   var remarks = e.parameter.remarks;
     *   var d = new Date();
     *   var ctime = d.toLocaleString();
     *
     *   sheet.appendRow([scannedData, ctime, sdate, stime, status, remarks]);
     * }
     */
}
