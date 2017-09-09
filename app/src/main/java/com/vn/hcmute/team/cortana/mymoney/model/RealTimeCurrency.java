package com.vn.hcmute.team.cortana.mymoney.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by infamouSs on 9/9/17.
 */

public class RealTimeCurrency {
    
    public double get(String cur_code) {
        switch (cur_code) {
            case "AED":
                return AED;
            case "AFN":
                return AFN;
            case "ALL":
                return ALL;
            case "AMD":
                return AMD;
            case "ANG":
                return ANG;
            case "AOA":
                return AOA;
            case "ARS":
                return ARS;
            case "AUD":
                return AUD;
            case "AWG":
                return AWG;
            case "AZN":
                return AZN;
            case "BAM":
                return BAM;
            case "BBD":
                return BBD;
            case "BDT":
                return BDT;
            case "BGN":
                return BGN;
            case "BHD":
                return BHD;
            case "BIF":
                return BIF;
            case "BMD":
                return BMD;
            case "BND":
                return BND;
            case "BOB":
                return BOB;
            case "BRL":
                return BRL;
            case "BSD":
                return BSD;
            case "BTC":
                return BTC;
            case "BTN":
                return BTN;
            case "BWP":
                return BWP;
            case "BYN":
                return BYN;
            case "BYR":
                return BYR;
            case "BZD":
                return BZD;
            case "CAD":
                return CAD;
            case "CDF":
                return CDF;
            case "CHF":
                return CHF;
            case "CLF":
                return CLF;
            case "CLP":
                return CLP;
            case "CNY":
                return CNY;
            case "COP":
                return COP;
            case "CRC":
                return CRC;
            case "CUC":
                return CUC;
            case "CUP":
                return CUP;
            case "CVE":
                return CVE;
            case "CZK":
                return CZK;
            case "DJF":
                return DJF;
            case "DKK":
                return DKK;
            case "DOP":
                return DOP;
            case "DZD":
                return DZD;
            case "EGP":
                return EGP;
            case "ERN":
                return ERN;
            case "ETB":
                return ETB;
            case "EUR":
                return EUR;
            case "FJD":
                return FJD;
            case "FKP":
                return FKP;
            case "GBP":
                return GBP;
            case "GEL":
                return GEL;
            case "GGP":
                return GGP;
            case "GHS":
                return GHS;
            case "GIP":
                return GIP;
            case "GMD":
                return GMD;
            case "GNF":
                return GNF;
            case "GTQ":
                return GTQ;
            case "GYD":
                return GYD;
            case "HKD":
                return HKD;
            case "HNL":
                return HNL;
            case "HRK":
                return HRK;
            case "HTG":
                return HTG;
            case "HUF":
                return HUF;
            case "IDR":
                return IDR;
            case "ILS":
                return ILS;
            case "IMP":
                return IMP;
            case "INR":
                return INR;
            case "IQD":
                return IQD;
            case "IRR":
                return IRR;
            case "ISK":
                return ISK;
            case "JEP":
                return JEP;
            case "JMD":
                return JMD;
            case "JOD":
                return JOD;
            case "JPY":
                return JPY;
            case "KES":
                return KES;
            case "KGS":
                return KGS;
            case "KHR":
                return KHR;
            case "KMF":
                return KMF;
            case "KPW":
                return KPW;
            case "KRW":
                return KRW;
            case "KWD":
                return KWD;
            case "KYD":
                return KYD;
            case "KZT":
                return KZT;
            case "LAK":
                return LAK;
            case "LBP":
                return LBP;
            case "LKR":
                return LKR;
            case "LRD":
                return LRD;
            case "LSL":
                return LSL;
            case "LTL":
                return LTL;
            case "LVL":
                return LVL;
            case "LYD":
                return LYD;
            case "MAD":
                return MAD;
            case "MDL":
                return MDL;
            case "MGA":
                return MGA;
            case "MKD":
                return MKD;
            case "MMK":
                return MMK;
            case "MNT":
                return MNT;
            case "MOP":
                return MOP;
            case "MRO":
                return MRO;
            case "MUR":
                return MUR;
            case "MVR":
                return MVR;
            case "MWK":
                return MWK;
            case "MXN":
                return MXN;
            case "MYR":
                return MYR;
            case "MZN":
                return MZN;
            case "NAD":
                return NAD;
            case "NGN":
                return NGN;
            case "NIO":
                return NIO;
            case "NOK":
                return NOK;
            case "NPR":
                return NPR;
            case "NZD":
                return NZD;
            case "OMR":
                return OMR;
            case "PAB":
                return PAB;
            case "PEN":
                return PEN;
            case "PGK":
                return PGK;
            case "PHP":
                return PHP;
            case "PKR":
                return PKR;
            case "PLN":
                return PLN;
            case "PYG":
                return PYG;
            case "QAR":
                return QAR;
            case "RON":
                return RON;
            case "RSD":
                return RSD;
            case "RUB":
                return RUB;
            case "RWF":
                return RWF;
            case "SAR":
                return SAR;
            case "SBD":
                return SBD;
            case "SCR":
                return SCR;
            case "SDG":
                return SDG;
            case "SEK":
                return SEK;
            case "SGD":
                return SGD;
            case "SHP":
                return SHP;
            case "SLL":
                return SLL;
            case "SOS":
                return SOS;
            case "SRD":
                return SRD;
            case "STD":
                return STD;
            case "SVC":
                return SVC;
            case "SYP":
                return SYP;
            case "SZL":
                return SZL;
            case "THB":
                return THB;
            case "TJS":
                return TJS;
            case "TMT":
                return TMT;
            case "TND":
                return TND;
            case "TOP":
                return TOP;
            case "TRY":
                return TRY;
            case "TTD":
                return TTD;
            case "TWD":
                return TWD;
            case "TZS":
                return TZS;
            case "UAH":
                return UAH;
            case "UGX":
                return UGX;
            case "USD":
                return USD;
            case "UYU":
                return UYU;
            case "UZS":
                return UZS;
            case "VEF":
                return VEF;
            case "VND":
                return VND;
            case "VUV":
                return VUV;
            case "WST":
                return WST;
            case "XAF":
                return XAF;
            case "XAG":
                return XAG;
            case "XAU":
                return XAU;
            case "XCD":
                return XCD;
            case "XDR":
                return XDR;
            case "XOF":
                return XOF;
            case "XPF":
                return XPF;
            case "YER":
                return YER;
            case "ZAR":
                return ZAR;
            case "ZMK":
                return ZMK;
            case "ZMW":
                return ZMW;
            case "ZWL":
                return ZWL;
            default:
                return 0;
        }
    }
    
    @SerializedName("USDAED")
    @Expose
    public double AED;
    @SerializedName("USDAFN")
    @Expose
    public double AFN;
    @SerializedName("USDALL")
    @Expose
    public double ALL;
    @SerializedName("USDAMD")
    @Expose
    public double AMD;
    @SerializedName("USDANG")
    @Expose
    public double ANG;
    @SerializedName("USDAOA")
    @Expose
    public double AOA;
    @SerializedName("USDARS")
    @Expose
    public double ARS;
    @SerializedName("USDAUD")
    @Expose
    public double AUD;
    @SerializedName("USDAWG")
    @Expose
    public double AWG;
    @SerializedName("USDAZN")
    @Expose
    public double AZN;
    @SerializedName("USDBAM")
    @Expose
    public double BAM;
    @SerializedName("USDBBD")
    @Expose
    public double BBD;
    @SerializedName("USDBDT")
    @Expose
    public double BDT;
    @SerializedName("USDBGN")
    @Expose
    public double BGN;
    @SerializedName("USDBHD")
    @Expose
    public double BHD;
    @SerializedName("USDBIF")
    @Expose
    public double BIF;
    @SerializedName("USDBMD")
    @Expose
    public Integer BMD;
    @SerializedName("USDBND")
    @Expose
    public double BND;
    @SerializedName("USDBOB")
    @Expose
    public double BOB;
    @SerializedName("USDBRL")
    @Expose
    public double BRL;
    @SerializedName("USDBSD")
    @Expose
    public Integer BSD;
    @SerializedName("USDBTC")
    @Expose
    public double BTC;
    @SerializedName("USDBTN")
    @Expose
    public double BTN;
    @SerializedName("USDBWP")
    @Expose
    public double BWP;
    @SerializedName("USDBYN")
    @Expose
    public double BYN;
    @SerializedName("USDBYR")
    @Expose
    public Integer BYR;
    @SerializedName("USDBZD")
    @Expose
    public double BZD;
    @SerializedName("USDCAD")
    @Expose
    public double CAD;
    @SerializedName("USDCDF")
    @Expose
    public double CDF;
    @SerializedName("USDCHF")
    @Expose
    public double CHF;
    @SerializedName("USDCLF")
    @Expose
    public double CLF;
    @SerializedName("USDCLP")
    @Expose
    public double CLP;
    @SerializedName("USDCNY")
    @Expose
    public double CNY;
    @SerializedName("USDCOP")
    @Expose
    public double COP;
    @SerializedName("USDCRC")
    @Expose
    public double CRC;
    @SerializedName("USDCUC")
    @Expose
    public Integer CUC;
    @SerializedName("USDCUP")
    @Expose
    public double CUP;
    @SerializedName("USDCVE")
    @Expose
    public double CVE;
    @SerializedName("USDCZK")
    @Expose
    public double CZK;
    @SerializedName("USDDJF")
    @Expose
    public double DJF;
    @SerializedName("USDDKK")
    @Expose
    public double DKK;
    @SerializedName("USDDOP")
    @Expose
    public double DOP;
    @SerializedName("USDDZD")
    @Expose
    public double DZD;
    @SerializedName("USDEGP")
    @Expose
    public double EGP;
    @SerializedName("USDERN")
    @Expose
    public double ERN;
    @SerializedName("USDETB")
    @Expose
    public double ETB;
    @SerializedName("USDEUR")
    @Expose
    public double EUR;
    @SerializedName("USDFJD")
    @Expose
    public double FJD;
    @SerializedName("USDFKP")
    @Expose
    public double FKP;
    @SerializedName("USDGBP")
    @Expose
    public double GBP;
    @SerializedName("USDGEL")
    @Expose
    public double GEL;
    @SerializedName("USDGGP")
    @Expose
    public double GGP;
    @SerializedName("USDGHS")
    @Expose
    public double GHS;
    @SerializedName("USDGIP")
    @Expose
    public double GIP;
    @SerializedName("USDGMD")
    @Expose
    public double GMD;
    @SerializedName("USDGNF")
    @Expose
    public double GNF;
    @SerializedName("USDGTQ")
    @Expose
    public double GTQ;
    @SerializedName("USDGYD")
    @Expose
    public double GYD;
    @SerializedName("USDHKD")
    @Expose
    public double HKD;
    @SerializedName("USDHNL")
    @Expose
    public double HNL;
    @SerializedName("USDHRK")
    @Expose
    public double HRK;
    @SerializedName("USDHTG")
    @Expose
    public double HTG;
    @SerializedName("USDHUF")
    @Expose
    public double HUF;
    @SerializedName("USDIDR")
    @Expose
    public Integer IDR;
    @SerializedName("USDILS")
    @Expose
    public double ILS;
    @SerializedName("USDIMP")
    @Expose
    public double IMP;
    @SerializedName("USDINR")
    @Expose
    public double INR;
    @SerializedName("USDIQD")
    @Expose
    public Integer IQD;
    @SerializedName("USDIRR")
    @Expose
    public double IRR;
    @SerializedName("USDISK")
    @Expose
    public double ISK;
    @SerializedName("USDJEP")
    @Expose
    public double JEP;
    @SerializedName("USDJMD")
    @Expose
    public double JMD;
    @SerializedName("USDJOD")
    @Expose
    public double JOD;
    @SerializedName("USDJPY")
    @Expose
    public double JPY;
    @SerializedName("USDKES")
    @Expose
    public double KES;
    @SerializedName("USDKGS")
    @Expose
    public double KGS;
    @SerializedName("USDKHR")
    @Expose
    public double KHR;
    @SerializedName("USDKMF")
    @Expose
    public double KMF;
    @SerializedName("USDKPW")
    @Expose
    public double KPW;
    @SerializedName("USDKRW")
    @Expose
    public double KRW;
    @SerializedName("USDKWD")
    @Expose
    public double KWD;
    @SerializedName("USDKYD")
    @Expose
    public double KYD;
    @SerializedName("USDKZT")
    @Expose
    public double KZT;
    @SerializedName("USDLAK")
    @Expose
    public double LAK;
    @SerializedName("USDLBP")
    @Expose
    public double LBP;
    @SerializedName("USDLKR")
    @Expose
    public double LKR;
    @SerializedName("USDLRD")
    @Expose
    public double LRD;
    @SerializedName("USDLSL")
    @Expose
    public double LSL;
    @SerializedName("USDLTL")
    @Expose
    public double LTL;
    @SerializedName("USDLVL")
    @Expose
    public double LVL;
    @SerializedName("USDLYD")
    @Expose
    public double LYD;
    @SerializedName("USDMAD")
    @Expose
    public double MAD;
    @SerializedName("USDMDL")
    @Expose
    public double MDL;
    @SerializedName("USDMGA")
    @Expose
    public double MGA;
    @SerializedName("USDMKD")
    @Expose
    public double MKD;
    @SerializedName("USDMMK")
    @Expose
    public double MMK;
    @SerializedName("USDMNT")
    @Expose
    public double MNT;
    @SerializedName("USDMOP")
    @Expose
    public double MOP;
    @SerializedName("USDMRO")
    @Expose
    public double MRO;
    @SerializedName("USDMUR")
    @Expose
    public double MUR;
    @SerializedName("USDMVR")
    @Expose
    public double MVR;
    @SerializedName("USDMWK")
    @Expose
    public double MWK;
    @SerializedName("USDMXN")
    @Expose
    public double MXN;
    @SerializedName("USDMYR")
    @Expose
    public double MYR;
    @SerializedName("USDMZN")
    @Expose
    public double MZN;
    @SerializedName("USDNAD")
    @Expose
    public double NAD;
    @SerializedName("USDNGN")
    @Expose
    public double NGN;
    @SerializedName("USDNIO")
    @Expose
    public double NIO;
    @SerializedName("USDNOK")
    @Expose
    public double NOK;
    @SerializedName("USDNPR")
    @Expose
    public double NPR;
    @SerializedName("USDNZD")
    @Expose
    public double NZD;
    @SerializedName("USDOMR")
    @Expose
    public double OMR;
    @SerializedName("USDPAB")
    @Expose
    public Integer PAB;
    @SerializedName("USDPEN")
    @Expose
    public double PEN;
    @SerializedName("USDPGK")
    @Expose
    public double PGK;
    @SerializedName("USDPHP")
    @Expose
    public double PHP;
    @SerializedName("USDPKR")
    @Expose
    public double PKR;
    @SerializedName("USDPLN")
    @Expose
    public double PLN;
    @SerializedName("USDPYG")
    @Expose
    public double PYG;
    @SerializedName("USDQAR")
    @Expose
    public double QAR;
    @SerializedName("USDRON")
    @Expose
    public double RON;
    @SerializedName("USDRSD")
    @Expose
    public double RSD;
    @SerializedName("USDRUB")
    @Expose
    public double RUB;
    @SerializedName("USDRWF")
    @Expose
    public double RWF;
    @SerializedName("USDSAR")
    @Expose
    public double SAR;
    @SerializedName("USDSBD")
    @Expose
    public double SBD;
    @SerializedName("USDSCR")
    @Expose
    public double SCR;
    @SerializedName("USDSDG")
    @Expose
    public double SDG;
    @SerializedName("USDSEK")
    @Expose
    public double SEK;
    @SerializedName("USDSGD")
    @Expose
    public double SGD;
    @SerializedName("USDSHP")
    @Expose
    public double SHP;
    @SerializedName("USDSLL")
    @Expose
    public double SLL;
    @SerializedName("USDSOS")
    @Expose
    public double SOS;
    @SerializedName("USDSRD")
    @Expose
    public double SRD;
    @SerializedName("USDSTD")
    @Expose
    public double STD;
    @SerializedName("USDSVC")
    @Expose
    public double SVC;
    @SerializedName("USDSYP")
    @Expose
    public double SYP;
    @SerializedName("USDSZL")
    @Expose
    public double SZL;
    @SerializedName("USDTHB")
    @Expose
    public double THB;
    @SerializedName("USDTJS")
    @Expose
    public double TJS;
    @SerializedName("USDTMT")
    @Expose
    public double TMT;
    @SerializedName("USDTND")
    @Expose
    public double TND;
    @SerializedName("USDTOP")
    @Expose
    public double TOP;
    @SerializedName("USDTRY")
    @Expose
    public double TRY;
    @SerializedName("USDTTD")
    @Expose
    public double TTD;
    @SerializedName("USDTWD")
    @Expose
    public double TWD;
    @SerializedName("USDTZS")
    @Expose
    public double TZS;
    @SerializedName("USDUAH")
    @Expose
    public double UAH;
    @SerializedName("USDUGX")
    @Expose
    public double UGX;
    @SerializedName("USDUSD")
    @Expose
    public Integer USD;
    @SerializedName("USDUYU")
    @Expose
    public double UYU;
    @SerializedName("USDUZS")
    @Expose
    public double UZS;
    @SerializedName("USDVEF")
    @Expose
    public double VEF;
    @SerializedName("USDVND")
    @Expose
    public Integer VND;
    @SerializedName("USDVUV")
    @Expose
    public double VUV;
    @SerializedName("USDWST")
    @Expose
    public double WST;
    @SerializedName("USDXAF")
    @Expose
    public double XAF;
    @SerializedName("USDXAG")
    @Expose
    public double XAG;
    @SerializedName("USDXAU")
    @Expose
    public double XAU;
    @SerializedName("USDXCD")
    @Expose
    public double XCD;
    @SerializedName("USDXDR")
    @Expose
    public double XDR;
    @SerializedName("USDXOF")
    @Expose
    public double XOF;
    @SerializedName("USDXPF")
    @Expose
    public double XPF;
    @SerializedName("USDYER")
    @Expose
    public double YER;
    @SerializedName("USDZAR")
    @Expose
    public double ZAR;
    @SerializedName("USDZMK")
    @Expose
    public double ZMK;
    @SerializedName("USDZMW")
    @Expose
    public double ZMW;
    @SerializedName("USDZWL")
    @Expose
    public double ZWL;
}
