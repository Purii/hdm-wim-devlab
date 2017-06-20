function selectHandler(e) {
    //alert('A table row was selected' +'______hier später neuen tab öffenen');
    document.getElementById('curve_chart').click();
}

google.charts.load('current', {'packages':['treemap']});
google.charts.setOnLoadCallback(drawChart);
function drawChart() {
    var data = google.visualization.arrayToDataTable([
        ['Location', 'Parent', 'Market trade volume (size)', 'Market increase/decrease (color)'],
        ['Global', null, 1, 0],
        ['America', 'Global', 10, 0],
        ['Europe', 'Global', 5, 0],
        ['Asia', 'Global', 6, 0],
        ['Australia', 'Global', 7, 1],
        ['Africa', 'Global', 1, 0]

    ]);

    tree = new google.visualization.TreeMap(document.getElementById('chart_div'));

    google.visualization.events.addListener(tree, 'select', selectHandler);

    tree.draw(data, {
        minColor: '#f00',
        midColor: '#ddd',
        maxColor: '#0d0',
        headerHeight: 15,
        fontColor: 'black',
        showScale: true
    });

}