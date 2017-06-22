
/*
var nameArray = ['starCars','mercedes_1','bmw', 'porsche', 'porschedoku1' ];
var parentArray = [null, 'starCars','starCars', 'starCars', 'starCars' ];
var sizeArray = [1,1,1,1,1 ];
var colorArray = [1,1,1, 25, 50, 100];
*/




function parameterFunction() {

    if (doDraw == true) {

        google.charts.load('current', { 'packages': ['treemap'] });
        google.charts.setOnLoadCallback(drawChart);
        function drawChart() {

            var data = new google.visualization.DataTable();
            data.addColumn('string', 'Location');
            data.addColumn('string', 'Parent');
            data.addColumn('number', 'size');
            data.addColumn('number', 'color');


            var rowArray = [];

            for (var y = 0; y < nameArray.length; y++) {

                rowArray.push([nameArray[y], parentArray[y], sizeArray[y], colorArray[y]]);
            }

            data.addRows(rowArray);

            tree = new google.visualization.TreeMap(document.getElementById('chart_div'));


            function showFullTooltip(row, size, value) {



                return '<div class="mouseOvertoKill' + '" style="background:#fd9; padding:10px;  border-style:solid">' +
                    '<span style="font-family:Courier"><b>' + data.getValue(row, 0) +
                    '</b>, ' + data.getValue(row, 1) + ', ' + data.getValue(row, 2) +
                    ', ' + data.getValue(row, 3) + '</span><br>' +
                    'Datatable row: ' + row + '<br>' +
                    data.getColumnLabel(2) +
                    ' (total value of this cell and its children): ' + size + '<br>' +
                    data.getColumnLabel(3) + ': ' + value + ' </div>';

            }


     

            google.visualization.events.addListener(tree, 'select', function () {
                var sel = tree.getSelection()[0];
                if (sel) {
                    var v = data.getValue(sel.row, 0);
                    //  alert('You have selected row ' + sel.row + ': ' + v);
                }
                addTab(v);
            });


            tree.draw(data, {
                minColor: '#FBF6EA',
                midColor: '#FCDC86',
                maxColor: '#FCB303',
                headerHeight: 0,
                fontColor: 'black',
                showScale: true,
                generateTooltip: showFullTooltip
            });
       deleteOldDivs();
        }
    }
}

function deleteOldDivs() {


try{

    var divs = document.getElementsByClassName("mouseOvertoKill");
    for (var i = 0; i < divs.length; i++) {
        divs[i].parentNode.remove();
      //  console.log(divs[i].parentNode);

    }}catch (e){

}
}

var doDraw = true;
document.getElementById("drawChartButton").onclick = parameterFunction;
/*
 document.getElementById('drawButton').addEventListener('onclick',
 parameterFunction())

 */
