//For a pie chart
var chartDataStr = decodeHtml(chartData);
var chartJSonArray = JSON.parse(chartDataStr);

var arrayLength = chartJSonArray.length;
var numericData = [];
var labelData = [];

for (var i = 0; i < arrayLength; i++) {
    numericData[i] = chartJSonArray[i].value;
    labelData[i] =chartJSonArray[i].label;
}

new Chart(document.getElementById("myPieChart"), {
    type: 'pie',
    //The data for our dataset

    data:{
    labels : labelData,
        datasets: [{
            label: 'My First dataset',
            backgroundColor: ["#3e95cd", "#8e5ea2", "#3cba9f"],

            data: numericData
        }]
    },

    //Configuration options go here
    options: {
        title:{
            display: true,
            text: "Project Status"
        }
    }
});

function decodeHtml(html) {
    var txt =document.createElement("textarea")
    txt.innerHTML = html;
    return txt.value;
}
