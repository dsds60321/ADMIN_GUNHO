(function ($) {
    var tfLineChart = (function () {

        var chartBar = function () {

            var options = {
                series: [
                    { name: 'Series 1', type: 'line', data: [10, 20, 30, 40, 50] },
                    { name: 'Series 2', type: 'line', data: [15, 25, 35, 45, 55] },
                    { name: 'Series 3', type: 'line', data: [20, 30, 40, 50, 60] },
                    { name: 'Series 4', type: 'line', data: [25, 35, 45, 55, 65] },
                    { name: 'Series 5', type: 'line', data: [30, 40, 50, 60, 70] },
                    { name: 'Series 6', type: 'line', data: [35, 45, 55, 65, 75] },
                    { name: 'Series 7', type: 'line', data: [40, 50, 60, 70, 80] },
                    { name: 'Series 8', type: 'line', data: [45, 55, 65, 75, 85] },
                    { name: 'Series 9', type: 'line', data: [50, 60, 70, 80, 90] },
                    { name: 'Series 10', type: 'line', data: [55, 65, 75, 85, 95] }
                ],
                chart: {
                    height: 387,
                    type: 'line', // 차트 타입
                    stacked: false,
                    toolbar: {
                        show: false,
                    },
                    animations: {
                        enabled: true,
                        easing: 'easeinout',
                        speed: 500,
                        animateGradually: {
                            enabled: true,
                            delay: 150
                        },
                        dynamicAnimation: {
                            enabled: true,
                            speed: 350
                        }
                    }
                },
                stroke: {
                    width: [2, 2, 2, 2, 2, 2, 2, 2, 2, 2],
                    curve: 'smooth'
                },
                dataLabels: {
                    enabled: false
                },
                legend: {
                    show: true,
                    position: 'top'
                },
                colors: ['#FF0000', '#FF7F00', '#FFFF00', '#7FFF00', '#00FF00', '#00FF7F', '#00FFFF', '#007FFF', '#0000FF', '#7F00FF'], // 10개 색상 지정
                xaxis: {
                    labels: {
                        style: {
                            colors: '#95989D',
                        },
                    },
                    tooltip: {
                        enabled: false
                    },
                    categories: ["Category 1", "Category 2", "Category 3", "Category 4", "Category 5"]
                },
            };

            var chart = new ApexCharts(
                document.querySelector("#line-chart-22"),
                options
            );
            if ($("#line-chart-22").length > 0) {
                chart.render();
            }
        };

        /* Function ============ */
        return {
            load: function () {
                chartBar();
            },
        };
    })();

    jQuery(window).on("load", function () {
        tfLineChart.load();
    });

})(jQuery);
