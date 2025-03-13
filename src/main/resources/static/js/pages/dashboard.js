function summaryRendering(symbol, close) {
    document.getElementById('summary').innerHTML +=
    `<div>
        <div class="mb-1">
            <div class="block-legend">
                <div class="dot t7"></div>
                <div class="text-tiny">${symbol}</div>
            </div>
        </div>
        <div class="flex items-center gap12">
            <h4>${close}</h4>
<!--            <div class="box-icon-trending up">-->
<!--                <i class="icon-trending-up"></i>-->
<!--                <div class="body-title number text-grey">0.56%</div>-->
<!--            </div>-->
        </div>
    </div>`
}

(function ($) {
    var tfLineChart = (function () {
        // 차트 생성 메인 함수
        var chartBar = function () {
            // /stock API에서 데이터 가져오기
            fetch('/stock')
                .then((response) => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then((data) => {
                    console.log('Data received from API:', data);
                    // 데이터 검증
                    if (!data) {
                        throw new Error('Invalid or empty data received from API');
                    }

                    // 주식 심볼별 데이터를 순회
                    Object.keys(data.res).forEach((stockSymbol) => {
                        let lastClose;
                        
                        const stockEntries = data.res[stockSymbol]; // 심볼의 데이터 가져오기

                        if (!Array.isArray(stockEntries) || stockEntries.length === 0) {
                            console.warn(`No valid entries for stock: ${stockSymbol}`);
                            return;
                        }

                        // 데이터 변환: 날짜 및 주식 정보 추출
                        const processedEntries = stockEntries.map((entry) => {
                            const [date, value] = Object.entries(entry)[0]; // 첫 번째 키-값 추출
                            const parsedValue = JSON.parse(value); // JSON 문자열 파싱
                            return {
                                date, // 날짜
                                ...parsedValue, // high, low, close, volume, open 등 추가
                            };
                        });

                        lastClose = processedEntries[0].close;

                        console.log('Processed entries:', processedEntries);

                        // 심볼 데이터로 차트를 생성
                        createChartForStock(stockSymbol, processedEntries);

                        summaryRendering(stockSymbol, lastClose);
                    });
                    
                    
                })
                .catch((error) => {
                    console.error('Error fetching or processing data:', error); // 오류 처리
                });
        };
        

        // 주식 심볼에 대한 차트 생성 함수
        var createChartForStock = function (stockSymbol, stockEntries) {
            // 데이터 분리: 날짜 및 종가(close) 데이터 추출
            const categories = stockEntries.map((entry) => entry.date); // x축: 날짜
            const closePrices = stockEntries.map((entry) => parseFloat(entry.close)); // y축: 종가

            // 시리즈 데이터 구성
            const series = [
                {
                    name: `${stockSymbol} Close Price`,
                    type: 'line',
                    data: closePrices,
                },
            ];

            // 차트 렌더링
            renderChart(stockSymbol, categories, series);
        };

        const getRandomColor = () => {
            const letters = '0123456789ABCDEF';
            let color = '#';
            for (let i = 0; i < 6; i++) {
                color += letters[Math.floor(Math.random() * 16)]; // 0~15까지의 랜덤 수를 16진수로 변환
            }
            return color;
        };


        // 차트 렌더링 함수
        var renderChart = function (stockSymbol, categories, series) {
            const chartContainerId = `chart-${stockSymbol}`; // 동적 ID 생성
            let chartContainer = document.querySelector(`#${chartContainerId}`);

            // 컨테이너가 없으면 생성
            if (!chartContainer) {
                chartContainer = document.createElement('div');
                chartContainer.id = chartContainerId;
                chartContainer.style.height = '400px';
                chartContainer.style.marginBottom = '20px';
                document.querySelector('#charts-container').appendChild(chartContainer);
            }

            const options = {
                series: series,
                chart: {
                    height: 400,
                    type: 'line',
                    toolbar: {
                        show: false,
                    },
                    animations: {
                        enabled: true,
                        easing: 'easeinout',
                        speed: 500,
                        animateGradually: {
                            enabled: true,
                            delay: 150,
                        },
                        dynamicAnimation: {
                            enabled: true,
                            speed: 350,
                        },
                    },
                },
                stroke: {
                    width: [2],
                    curve: 'smooth', // 부드러운 곡선
                },
                dataLabels: {
                    enabled: true, // 데이터 라벨 표시
                },
                legend: {
                    show: true,
                    position: 'top',
                },
                xaxis: {
                    labels: {
                        style: {
                            colors: '#95989D',
                        },
                    },
                    categories: categories, // 날짜 데이터
                },
                colors: [getRandomColor()], // 시리즈 색상
            };

            // ApexCharts 인스턴스 생성 및 렌더링
            const chart = new ApexCharts(chartContainer, options);
            chart.render();
        };

        return {
            init: chartBar, // 초기화 메서드
        };
    })();

    // DOM 로드 후 실행
    $(document).ready(function () {
        tfLineChart.init();
    });
})(jQuery);

