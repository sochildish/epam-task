package com.epam.test.bean;

/**
 * @author xu.song
 * @date 2021/7/29 23:51
 */
public class WeatherBean {

    private WeatherInfo weatherInfo;

    public WeatherBean() {
    }

    public WeatherBean(WeatherInfo weatherInfo) {
        this.weatherInfo = weatherInfo;
    }

    public WeatherInfo getWeatherInfo() {
        return weatherInfo;
    }

    public void setWeatherInfo(WeatherInfo weatherInfo) {
        this.weatherInfo = weatherInfo;
    }

    public static class WeatherInfo {

        private String AP;
        private String Radar;
        private String SD;
        private String WD;
        private String WS;
        private String WSE;
        private String city;
        private String cityid;
        private String isRadar;
        private String njd;
        private String sm;
        private String temp;
        private String time;

        public WeatherInfo() {
        }

        public WeatherInfo(String AP, String radar, String SD, String WD, String WS, String WSE, String city, String cityid, String isRadar, String njd, String sm, String temp, String time) {
            this.AP = AP;
            Radar = radar;
            this.SD = SD;
            this.WD = WD;
            this.WS = WS;
            this.WSE = WSE;
            this.city = city;
            this.cityid = cityid;
            this.isRadar = isRadar;
            this.njd = njd;
            this.sm = sm;
            this.temp = temp;
            this.time = time;
        }

        public String getAP() {
            return AP;
        }

        public void setAP(String AP) {
            this.AP = AP;
        }

        public String getRadar() {
            return Radar;
        }

        public void setRadar(String radar) {
            Radar = radar;
        }

        public String getSD() {
            return SD;
        }

        public void setSD(String SD) {
            this.SD = SD;
        }

        public String getWD() {
            return WD;
        }

        public void setWD(String WD) {
            this.WD = WD;
        }

        public String getWS() {
            return WS;
        }

        public void setWS(String WS) {
            this.WS = WS;
        }

        public String getWSE() {
            return WSE;
        }

        public void setWSE(String WSE) {
            this.WSE = WSE;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCityid() {
            return cityid;
        }

        public void setCityid(String cityid) {
            this.cityid = cityid;
        }

        public String getIsRadar() {
            return isRadar;
        }

        public void setIsRadar(String isRadar) {
            this.isRadar = isRadar;
        }

        public String getNjd() {
            return njd;
        }

        public void setNjd(String njd) {
            this.njd = njd;
        }

        public String getSm() {
            return sm;
        }

        public void setSm(String sm) {
            this.sm = sm;
        }

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
