## **기본 소개**
- **무료 계정:** Alpha Vantage는 무료 계정을 사용하여 API 호출이 가능합니다.
  단, 무료 계정에서는 시간 당 API 호출 수가 제한되며, 유료 플랜에서 더 높은 호출 한도를 제공합니다.
    - 무료로 **분당 5회 호출** 할 수 있습니다.

### **1. Intraday Time Series (실시간 주식 데이터)**
- 분 단위로 주식 데이터를 제공합니다.
- 일반적으로 실시간 가격 조회나 단기 분석에 적합합니다.

**예제 요청:**
``` bash
https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=IBM&interval=5min&apikey=demo
```
**주요 매개변수:**
1. `symbol`: 주식 코드 (예: `symbol=IBM`)
2. `interval`: 시간 간격 (1min, 5min, 15min, 30min, 60min 사용할 수 있음)
3. `apikey`: API 키 (무료 제공)

### **2. Daily Time Series (일 단위 주식 데이터)**
- 특정 주식의 하루 단위 가격 데이터 (시가, 종가 등)를 제공합니다.
- 최대 과거 **20년 데이터**를 제공.

**예제 요청:**
``` bash
https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=IBM&apikey=demo
```
**주요 매개변수:**
1. `symbol`: 주식 코드 (예: `symbol=IBM`)
2. `outputsize`: `compact` 또는 `full` (옵션, 기본값은 `compact`, 전체 데이터를 가져오려면 `full` 입력)

### **3. Weekly Time Series (주 단위 주식 데이터)**
- 주 단위로 집계된 데이터를 제공합니다.
- 장기적인 추세 분석에 유용합니다.

**예제 요청:**
``` bash
https://www.alphavantage.co/query?function=TIME_SERIES_WEEKLY&symbol=IBM&apikey=demo
```
**주요 매개변수:**
1. `symbol`: 주식 코드 (예: `symbol=IBM`)

### **4. Monthly Time Series (월 단위 주식 데이터)**
- 월 단위로 집계된 데이터를 제공합니다.
- 특정 주식의 장기적인 성과를 평가할 때 사용.

**예제 요청:**
``` bash
https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY&symbol=IBM&apikey=demo
```
**주요 매개변수:**
1. `symbol`: 주식 코드 (예: `symbol=IBM`)

### **5. Global Quote (실시간 주식 가격)**
- 주식의 최신 거래 가격, 변동률, 거래량 등 최신 정보를 제공합니다.
- 현재 상태를 확인하고 싶을 때 사용.

**예제 요청:**
``` bash
https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=IBM&apikey=demo
```
**주요 매개변수:**
1. `symbol`: 주식 코드 (예: `symbol=IBM`)

### **6. Sector Performances (섹터 수익률 분석)**
- 특정 기간 동안의 섹터별 주식의 성과를 제공합니다.
- 비교적 간단한 섹터 분석 데이터를 얻는 데 유용합니다.

**예제 요청:**
``` bash
https://www.alphavantage.co/query?function=SECTOR&apikey=demo
```
### **7. 암호화폐 관련 데이터**
- 거래소에서 암호화폐 데이터를 제공하며, 일별/실시간 데이터를 얻을 수 있습니다.

#### 7.1. 실시간 암호화폐 환율 데이터
- 두 개의 암호화폐 환율 데이터를 제공합니다.

**예제 요청:**
``` bash
https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=BTC&to_currency=USD&apikey=demo
```
**매개변수:**
1. `from_currency`: 환율의 기준이 되는 암호화폐 (예: BTC)
2. `to_currency`: 변환 대상 암호화폐 또는 법정화폐 (예: USD)

#### 7.2. 일별 암호화폐 데이터
- 특정 암호화폐의 종가 데이터를 제공합니다.

**예제 요청:**
``` bash
https://www.alphavantage.co/query?function=DIGITAL_CURRENCY_DAILY&symbol=BTC&market=USD&apikey=demo
```
**매개변수:**
1. `symbol`: 암호화폐 코드 (예: BTC)
2. `market`: 거래소 기준의 화폐 단위 (예: USD)

### **8. 외환(FX) 환율 데이터**
- 주요 통화 쌍의 환율 정보를 제공합니다.

**예제 요청:**
``` bash
https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=EUR&to_currency=USD&apikey=demo
```
**매개변수:**
1. `from_currency`: 기준 통화 (예: EUR)
2. `to_currency`: 변환 통화 (예: USD)

### **9. 경제 지표 (이코노믹 데이터)**
- 주요 경제 데이터 (예: GDP, 실업률 등)를 제공합니다.
- 경제 지표에 따른 주식시장 분석에 유용합니다.

## **API 응답 데이터 형식**
Alpha Vantage는 기본적으로 JSON 데이터를 제공합니다.
필요에 따라 `datatype` 매개변수를 통해 CSV 형식으로 데이터를 요청할 수도 있습니다.
``` bash
datatype=csv
```
## **요약**
무료로 사용할 수 있는 API 목록:
1. 실시간 주식 데이터 (`TIME_SERIES_INTRADAY`)
2. 일별 주식 데이터 (`TIME_SERIES_DAILY`)
3. 주별/월별 주식 데이터 (`TIME_SERIES_WEEKLY`, `TIME_SERIES_MONTHLY`)
4. 주식의 최신 가격 (`GLOBAL_QUOTE`)
5. 섹터 수익률 분석 (`SECTOR`)
6. 외환 데이터 (`CURRENCY_EXCHANGE_RATE`)
7. 암호화폐 데이터 (`DIGITAL_CURRENCY_DAILY`, `CURRENCY_EXCHANGE_RATE`)
