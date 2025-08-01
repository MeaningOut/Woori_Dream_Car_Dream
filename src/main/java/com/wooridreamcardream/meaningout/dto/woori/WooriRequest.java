package com.wooridreamcardream.meaningout.dto.woori;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WooriRequest {
    private DataHeader dataHeader;
    private DataBody dataBody;

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataHeader {
        private String UTZPE_CNCT_IPAD = "";
        private String UTZPE_CNCT_MCHR_UNQ_ID = "";
        private String UTZPE_CNCT_TEL_NO_TXT = "";
        private String UTZPE_CNCT_MCHR_IDF_SRNO = "";
        private String UTZ_MCHR_OS_DSCD = "";
        private String UTZ_MCHR_OS_VER_NM = "";
        private String UTZ_MCHR_MDL_NM = "";
        private String UTZ_MCHR_APP_VER_NM = "";
    }

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataBody {
        private String DBPE_ANL_ICM_AM;
        private String CAR_PR;
        private String GRN_NCAR_YN = "Y";
        private String CRINF_INQ_AGR_YN = "Y";
        private String INF_OFR_MND_AGR_YN = "Y";
        private String GAT_UTZ_MND_AGR_YN = "Y";
        private String CUS_IDF_INF_AGR_YN = "Y";
        private String INF_OFR_CHC_AGR_YN = "Y";
        private String GAT_UTZ_CHC_AGR_YN = "Y";
    }
}

