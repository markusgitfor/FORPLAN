/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package forplanner;

/**
 *
 * @author markus
 */
public class Models {

    public Models() {

    }

    double volume_laasasenaho(double d13, double height, double sp) {
        // Taper curve and volume functions for pine, spruce and birch, Laasasenaho Jouko (1982)
        // d13, height
        // http://urn.fi/URN:ISBN:951-40-0589-9

        double volume = 0;
        if (sp == 1) {
            volume = 0.036089 * Math.pow(d13, 2.01395) * Math.pow(0.99676, 2.0) * Math.pow(height, 2.07025) * Math.pow((height - 1.3), -1.07209);
        }
        if (sp == 2) {
            volume = 0.022927 * Math.pow(d13, 1.91505) * Math.pow(0.99146, d13) * Math.pow(height, 2.82541) * Math.pow((height - 1.3), -1.53547);
        }
        if (sp == 3) {
            volume = 0.011197 * Math.pow(d13, 2.10253) * Math.pow(0.986, d13) * Math.pow(height, 3.98519) * Math.pow((height - 1.3), -2.659);
        }
        return volume; // dm3

    }
    
    public static double inGrowthModel_Pukkala(double G, double N_spruce, double N_other, double mt, double sp) {

        double ingrowth = 0;
        // Exponenttifuktioksi  + 1
        if (sp == 2) {
            ingrowth = Math.exp((4.4688 + (-0.712) * Math.sqrt(G) + (0) * Math.log(G) + 0.083 * Math.sqrt(N_spruce) + (0) * (N_other) + (-0.567) * mt) + 1);

        } else {
            ingrowth = Math.exp((6.154 + (0) * Math.sqrt(G) + (-1.683) * Math.log(G) + 0 * Math.sqrt(N_spruce) + (0.0642) * (N_other) + (0) * mt) + 1);

        }
        return ingrowth;

    }

    public static double meanDIngrowth_Pukkala(double G, double mt, double vt, int sp) {
        double mean = 0;

        if (sp == 2) {
            mean = Math.exp(2.004 + (-0.101) * Math.log(G) + (-0.0176) * mt + (-0.0646) * vt);
        } else {
            mean = Math.exp(1.958 + (-0.0841) * Math.log(G) + (-0.0425) * mt + (-0.0556) * vt);

        }
        return mean;

    }

    public static double survivalModel_Pukkala(double BALSpruce, double BAL, double G, double sp, double d13) {
        // Growth and yield models for uneven-sized forest stands in Finland, Pukkala (2009)

        //double BALSpruce = 20;
        //double BAL = 20;
        //double G = 20;
        double survival = 0;

        if (sp == 2) {
            survival = 1 / (1 + Math.exp(-(4.418 + 1.423 * Math.sqrt(d13) + (-1.046) * Math.log(G) + (-0.0954) * BALSpruce + 0 * BAL)));
            survival = Math.pow(survival, (5 / 6));
            System.out.println("");
            return survival;
        } else {
            survival = 1 / (1 + Math.exp(-(0.496 + 1.649 * Math.sqrt(d13) + (0) * Math.log(G) + (0) * BALSpruce + (-0.106) * BAL)));
            survival = Math.pow(survival, (5 / 6));
            return survival;
        }

    }
    
    static double d13_increment_Pukkala(double BAL_spruce, double BAL_other, double G, double d13, double SC, double TS, int sp) {
        
        int mt = 0, vt=0, ct=0, cit=0;

        if (SC == 1) {
            mt = 1;
            vt = 0;
            ct = 0;
            cit = 0;
        }
        if (SC == 2) {
            mt = 0;
            vt = 1;
            ct = 0;
            cit = 0;
        }
        if (SC == 3) {
            mt = 0;
            vt = 0;
            ct = 1;
            cit = 0;
        }
        if (SC == 4) {
            mt = 0;
            vt = 0;
            ct = 0;
            cit = 1;
        }

        
        double growth = 0;
        if(sp == 1){
            growth = -7.758 + -0.0335 * BAL_spruce + -0.0530 * BAL_other + -0.266 * Math.log(G) + 0.237 * Math.sqrt(d13) + -0.000901 * Math.pow(d13,2) + 
                    -0.238 * mt + -0.333*vt + -0.612 * ct + -1.201 * cit + 1.229 * Math.log(TS);
        }else if(sp == 2){
             growth = -5.317 + -0.0430 * BAL_spruce + -0.0106 * BAL_other + -0.486 * Math.log(G) + 0.455 * Math.sqrt(d13) + -0.000927 * Math.pow(d13,2) + 
                    -0.180 * mt + -0.450*vt + -0.929 * ct + 0 * cit + 0.823 *Math.log(TS);
        }else if(sp == 3){
             growth = -11.873 + -0.0474 * BAL_spruce + -0.0304 * BAL_other + -0.173 * Math.log(G) + 0.446 * Math.sqrt(d13) + -0.00123 * Math.pow(d13,2) + 
                    -0.121 * mt + -0.227*vt + -0.524 * ct + 0 * cit + 1.627 * Math.log(TS);
        }
        return Math.exp(growth);
        
        
    }
    
    public double mortalityModel_Hynynen(double d, double ba, double BAL) {

        //Hynynen et al. 2002
    
        double a0 = 0; // parametrit tarvitaan
        double a1 = 0;
        double a2 = 0;
        double a3 = 0;
        double res = 1 / Math.exp(a0 + a1 * d + a2 * ba + a3 * BAL);
        return res;

    }

    static double heightModel_uneven(int mt, int vt, int ct, int cit, int sp, double d13) {
        // Growth and yield models for uneven-sized forest stands in Finland, Pukkala (2009)
        double height = 0;

        switch (sp) {
            case 1:
                height = (25.014 + 7.68 * mt + 6.376 * vt + (-1.787) * ct + (-3.296) * cit) / (1 + (19.260 / d13) + (31.721 / Math.pow(d13, 2)));
                return height;
            case 2:
                height = (33.726 + 5.965 * mt + 2.178 * vt + (-1.399) * ct + (0) * cit) / (1 + (25.683 / d13) + (37.785 / Math.pow(d13, 2)));
                return height;
            case 3:
                height = (29.375 + 7.714 * mt + 3.059 * vt + (-2.87) * ct + (0) * cit) / (1 + (22.64 / d13) + (-8 / Math.pow(d13, 2)));
                return height;
            default:
                break;
        }
        return height;

    }

    double height_Growth_Spruce_Hynynen(double i_Hdom5, double d, double D_domvmi, double cr, double CR_domvmi, double RDF_L) {
        if (d < 0.1) {
            d = 0.1;
        }

        double res = i_Hdom5 * Math.pow((d / D_domvmi), (1.78842 * Math.pow(i_Hdom5, 0.2544) - 0.38878 * (cr / CR_domvmi) - 1.96189 * cr + 0.53034 * RDF_L));
        return res;
    }

    double height_Growth_Birch_Hynynen(double i_Hdom5, double d, double D_domvmi, double cr, double CR_domvmi, double RDF_L, double PLANT) {
        if (d < 0.1) {
            d = 0.1;
        }

        double res = i_Hdom5 * Math.pow((d / D_domvmi), ((1.54405 - 0.81202 * PLANT) * RDF_L));
        return res;

    }

    double height_Growth_Pine_Hynynen(double i_Hdom5, double d, double D_domvmi, double cr, double CR_domvmi, double RDF_L) {

        if (d < 0.1) {
            d = 0.1;
        }

        double res = i_Hdom5 * Math.pow((d / D_domvmi), (0.80171 * Math.pow(i_Hdom5, 0.4353) - 0.49724 * (cr / CR_domvmi) - 0.64383 * cr + 0.08193 * RDF_L));
        return res;
    }

    int Height_pine_Veltheim(double d, double Age, double BA, double D_gM, double TS, double ALT, double SC,
            int nres, double modelresult, char errors, int errorCheckMode,
            double allowedRiskLevel, double rectFactor
    ) {

        int ret = 1;

        /*if (errorCheckMode == 2)
	{
		// Evaluate the riskfunction for the model
		// Palauttaako riskimalli jonkin arvon mallin kutsujalle?
		// Jos palauttaa, voisiko viestin välittää warnings tai
		// errors osoittimissa?
	}*/
        int SC1_2, SC4, SC5, SC6_7;
        int iSC = (int) SC;
        switch (iSC) {
            case 1:
                SC1_2 = 1;
                SC4 = 0;
                SC5 = 0;
                SC6_7 = 0;
                break;
            case 2:
                SC1_2 = 1;
                SC4 = 0;
                SC5 = 0;
                SC6_7 = 0;
                break;
            case 3:
                SC1_2 = 0;
                SC4 = 0;
                SC5 = 0;
                SC6_7 = 0;
                break;
            case 4:
                SC1_2 = 0;
                SC4 = 1;
                SC5 = 0;
                SC6_7 = 0;
                break;
            case 5:
                SC1_2 = 0;
                SC4 = 0;
                SC5 = 1;
                SC6_7 = 0;
                break;
            case 6:
                SC1_2 = 0;
                SC4 = 0;
                SC5 = 0;
                SC6_7 = 1;
                break;
            case 7:
                SC1_2 = 0;
                SC4 = 0;
                SC5 = 0;
                SC6_7 = 1;
                break;
            // SC 8 not present in original publication, treated here as SC 6/7
            case 8:
                SC1_2 = 0;
                SC4 = 0;
                SC5 = 0;
                SC6_7 = 1;
                break;
            // defaults to SC==3, sensible?
            default:
                SC1_2 = 0;
                SC4 = 0;
                SC5 = 0;
                SC6_7 = 0;
                break;
        }

        //Check for fatal errors
        if (d <= 0) {
            System.out.println("Error");

        }
        if (Age <= 0) {
            System.out.println("Error");
        }
        if (D_gM <= 0) {
            System.out.println("Error");
        }
        if (ret == 0) {
            System.out.println("Error");
            return 0;
        }

        nres = 1;
        double res = 1.3 + Math.exp(3.1077 - 18.4669 / (d + 5) - 20.8014 * (1 / (Age + 10)) + 0.00927384 * BA - 0.13927 * (d / D_gM) + 166.004 / (TS / 10) - 12278.5 / Math.pow((TS / 10.0), 2)
                + 0.00443761 * (ALT / 10) + 0.0079877 * (SC1_2) - 0.0474632 * (SC4) - 0.117699 * (SC5) - 0.20109 * (SC6_7) + Math.pow(0.144925, 2) / 2);
        modelresult = res * rectFactor;
        return ret;
    }

    int Height_spruce_Veltheim(double d, double Age, double BA, double D_gM, double TS, double ALT, double SC,
            int nres, double modelresult, char errors, int errorCheckMode,
            double allowedRiskLevel, double rectFactor) {

        int ret = 1;

        // Check for the validity of the parameters
        int SC1_2, SC4_7;
        int iSC = (int) SC;

        switch (iSC) {
            case 1:
                SC1_2 = 1;
                SC4_7 = 0;
                break;
            case 2:
                SC1_2 = 1;
                SC4_7 = 0;
                break;
            case 3:
                SC1_2 = 0;
                SC4_7 = 0;
                break;
            case 4:
                SC1_2 = 0;
                SC4_7 = 1;
                break;
            case 5:
                SC1_2 = 0;
                SC4_7 = 1;
                break;
            case 6:
                SC1_2 = 0;
                SC4_7 = 1;
                break;
            case 7:
                SC1_2 = 0;
                SC4_7 = 1;
                break;
            // SC 8 not present in original publication, treated here as SC 4/7
            case 8:
                SC1_2 = 0;
                SC4_7 = 1;
                break;
            // defaults to SC==3, sensible?
            default:
                SC1_2 = 0;
                SC4_7 = 0;
                break;
        }

        if (d <= 0) {
            ret = 0;

        }
        if (Age <= 0) {
            ret = 0;

        }
        if (D_gM <= 0) {
            ret = 0;

        }
        if (ret == 0) {

            return 0;
        }

        nres = 1;
        double res = 1.3 + Math.exp(3.28747 - 27.8738 / (d + 5) + 19.6503 / Math.pow((d + 5), 2) + 0.000596058 * Age + 0.00758229 * BA - 0.145859 * (d / D_gM) + 0.0039764 * (TS / 10)
                - 0.00200392 * ALT / 10 - 0.013007 * (SC1_2) - 0.0544321 * (SC4_7) + Math.pow(0.137671, 2) / 2);

        modelresult = res * rectFactor;
        return ret;
    }

    int Height_white_birch_Veltheim(double d, double Age, double BA, double D_gM, double TS, double ALT, double SC,
            int nres, double modelresult, char errors, int errorCheckMode,
            double allowedRiskLevel, double rectFactor) {
        int ret = 1;

        int SC1_2, SC4_7;

        int iSC = (int) SC;

        switch (iSC) {
            case 1:
                SC1_2 = 1;
                SC4_7 = 0;
                break;
            case 2:
                SC1_2 = 1;
                SC4_7 = 0;
                break;
            case 3:
                SC1_2 = 0;
                SC4_7 = 0;
                break;
            case 4:
                SC1_2 = 0;
                SC4_7 = 1;
                break;
            case 5:
                SC1_2 = 0;
                SC4_7 = 1;
                break;
            case 6:
                SC1_2 = 0;
                SC4_7 = 1;
                break;
            case 7:
                SC1_2 = 0;
                SC4_7 = 1;
                break;
            // SC 8 not present in original publication, treated here as SC 4/7
            case 8:
                SC1_2 = 0;
                SC4_7 = 1;
                break;
            // defaults to SC==3, sensible?
            default:
                SC1_2 = 0;
                SC4_7 = 0;
                break;
        }

        //Check for fatal errors
        if (d <= 0) {
            ret = 0;

        }
        if (Age <= 0) {
            ret = 0;

        }
        if (D_gM <= 0) {
            ret = 0;

        }
        if (ret == 0) {

            return 0;
        }

        nres = 1;

        double res = 1.3 + Math.exp(0.0924547 - 15.9087 / (d + 5) - 16.1394 / Math.pow((d + 5), 2) + 0.00236413 * Age + 0.0152315 * BA - 0.000157243 * Math.pow(BA, 2) - 0.0752347 * (d / D_gM)
                + 0.0424791 * (TS / 10) - 0.000145416 * Math.pow((TS / 10), 2) + 0.0097927 * ALT / 10 + 0.0245652 * (SC1_2) - 0.0656305 * (SC4_7) + Math.pow(0.135363, 2) / 2);
        modelresult = res * rectFactor;
        return ret;
    }

    int Height_pubescent_birch_Veltheim(double d, double Age, double BA, double D_gM, double TS, double SC,
            int nres, double modelresult, char errors, int errorCheckMode,
            double allowedRiskLevel, double rectFactor) {
        int ret = 1;

        int SC1_2, SC4_7;

        int iSC = (int) SC;

        switch (iSC) {
            case 1:
                SC1_2 = 1;
                SC4_7 = 0;
                break;
            case 2:
                SC1_2 = 1;
                SC4_7 = 0;
                break;
            case 3:
                SC1_2 = 0;
                SC4_7 = 0;
                break;
            case 4:
                SC1_2 = 0;
                SC4_7 = 1;
                break;
            case 5:
                SC1_2 = 0;
                SC4_7 = 1;
                break;
            case 6:
                SC1_2 = 0;
                SC4_7 = 1;
                break;
            case 7:
                SC1_2 = 0;
                SC4_7 = 1;
                break;
            // SC 8 not present in original publication, treated here as SC 4/7
            case 8:
                SC1_2 = 0;
                SC4_7 = 1;
                break;
            // defaults to SC==3, sensible?
            default:
                SC1_2 = 0;
                SC4_7 = 0;
                break;
        }

        //Check for fatal errors
        if (d <= 0) {
            ret = 0;

        }
        if (Age <= 0) {
            ret = 0;

        }
        if (D_gM <= 0) {
            ret = 0;

        }
        if (ret == 0) {

            return 0;
        }

        nres = 1;

        double res = 1.3 + Math.exp(2.34571 - 15.4407 / (d + 5) - 7.23954 / Math.pow((d + 5), 2) - 10.8662 / (Age + 10) + 128.857 / Math.pow((Age + 10), 2) + 0.00800176 * BA - 0.0292937 * (d / D_gM)
                + 288.903 / (TS / 10) - 19611 / Math.pow((TS / 10), 2) + 0.0229426 * (SC1_2) - 0.0435982 * (SC4_7) + Math.pow(0.156964, 2) / 2);

        modelresult = res * rectFactor;
        return ret;
    }

    int Height_pine_peatland_Veltheim(double d, double Age, double BA, double D_gM, double TS, double ALT, double PEAT_transf, double SC,
            int nres, double modelresult, char errors, int errorCheckMode,
            double allowedRiskLevel, double rectFactor) {
        int ret = 1;

        int K1_3_R1_3, R5_6;

        int iSC = (int) SC;

        switch (iSC) {
            case 1:
                K1_3_R1_3 = 1;
                R5_6 = 0;
                break;
            case 2:
                K1_3_R1_3 = 1;
                R5_6 = 0;
                break;
            case 3:
                K1_3_R1_3 = 1;
                R5_6 = 0;
                break;
            case 4:
                K1_3_R1_3 = 0;
                R5_6 = 0;
                break;
            case 5:
                K1_3_R1_3 = 0;
                R5_6 = 1;
                break;
            case 6:
                K1_3_R1_3 = 0;
                R5_6 = 1;
                break;
            // defaults to SC==4 (K4 or R4), sensible?
            default:
                K1_3_R1_3 = 0;
                R5_6 = 0;
                break;
        }

        //Check for fatal errors
        if (d <= 0) {
            ret = 0;

        }
        if (Age <= 0) {
            ret = 0;

        }
        if (D_gM <= 0) {
            ret = 0;

        }
        if (ret == 0) {

            return 0;
        }

        nres = 1;

        double res = 1.3 + Math.exp(3.17438 - 12.3585 / (d + 5) - 44.4167 / Math.pow((d + 5), 2) - 20.4126 / (Age + 10) + 266.011 / Math.pow((Age + 10), 2) + 0.0291349 * BA - 0.000319285 * Math.pow(BA, 2)
                - 0.0609424 * (d / D_gM) - 37.5727 / (TS / 10) + 0.00497042 * ALT / 10 + 0.0541735 * (K1_3_R1_3) - 0.0740786 * (R5_6) + 0.0474278 * PEAT_transf + Math.pow(0.172876, 2) / 2);

        modelresult = res * rectFactor;
        return ret;
    }

    int Height_spruce_peatland_Veltheim(double d, double BA, double D_gM, double TS, double PEAT_transf, double SC,
            int nres, double modelresult, char errors, int errorCheckMode,
            double allowedRiskLevel, double rectFactor) {
        int ret = 1;

        int K1_2_R1_2, K4_R4_6;

        int iSC = (int) SC;

        switch (iSC) {
            case 1:
                K1_2_R1_2 = 1;
                K4_R4_6 = 0;
                break;
            case 2:
                K1_2_R1_2 = 1;
                K4_R4_6 = 0;
                break;
            case 3:
                K1_2_R1_2 = 0;
                K4_R4_6 = 0;
                break;
            case 4:
                K1_2_R1_2 = 0;
                K4_R4_6 = 1;
                break;
            case 5:
                K1_2_R1_2 = 0;
                K4_R4_6 = 1;
                break;
            case 6:
                K1_2_R1_2 = 0;
                K4_R4_6 = 1;
                break;
            // defaults to SC==3 (K3 or R3), sensible?
            default:
                K1_2_R1_2 = 0;
                K4_R4_6 = 0;
                break;
        }

        //Check for fatal errors
        if (d <= 0) {
            ret = 0;

        }
        if (D_gM <= 0) {
            ret = 0;

        }
        if (ret == 0) {

            return 0;
        }

        nres = 1;

        double res = 1.3 + Math.exp(1.51318 - 22.6924 / (d + 5) - 21.9424 / Math.pow((d + 5), 2) + 0.0213751 * BA - 0.000305414 * Math.pow(BA, 2) - 0.104563 * (d / D_gM) + 0.0292778 * (TS / 10)
                - 0.000110495 * Math.pow((TS / 10), 2) - 0.00238941 * (K1_2_R1_2) - 0.0641081 * (K4_R4_6) + 0.0128418 * PEAT_transf + Math.pow(0.152065, 2) / 2);

        modelresult = res * rectFactor;
        return ret;
    }

    int Height_birch_peatland_Veltheim(double d, double BA, double D_gM, double TS, double ALT, double PEAT_transf, double SC,
            int nres, double modelresult, char errors, int errorCheckMode,
            double allowedRiskLevel, double rectFactor) {
        int ret = 1;

        int K1_2_R1_2, K4_R4_6;

        int iSC = (int) SC;

        switch (iSC) {
            case 1:
                K1_2_R1_2 = 1;
                K4_R4_6 = 0;
                break;
            case 2:
                K1_2_R1_2 = 1;
                K4_R4_6 = 0;
                break;
            case 3:
                K1_2_R1_2 = 0;
                K4_R4_6 = 0;
                break;
            case 4:
                K1_2_R1_2 = 0;
                K4_R4_6 = 1;
                break;
            case 5:
                K1_2_R1_2 = 0;
                K4_R4_6 = 1;
                break;
            case 6:
                K1_2_R1_2 = 0;
                K4_R4_6 = 1;
                break;
            // defaults to SC==3 (K3 or R3), sensible?
            default:
                K1_2_R1_2 = 0;
                K4_R4_6 = 0;
                break;
        }

        //Check for fatal errors
        if (d <= 0) {
            ret = 0;

        }
        if (D_gM <= 0) {
            ret = 0;

        }
        if (ret == 0) {

            return 0;
        }

        nres = 1;

        double res = 1.3 + Math.exp(1.36109 - 16.1552 / (d + 5) + 0.0179231 * BA - 0.000181031 * Math.pow(BA, 2) - 0.0531461 * (d / D_gM) + 0.0269223 * (TS / 10) - 0.000100319 * Math.pow((TS / 10), 2)
                - 0.00283863 * ALT / 10 + 0.032024 * (K1_2_R1_2) - 0.0730828 * (K4_R4_6) + 0.0263218 * PEAT_transf + Math.pow(0.172894, 2) / 2);

        modelresult = res * rectFactor;
        return ret;

    }

    int Basal_area_growth_spruce_Hynynenym(double d, double cr, double SI_50, double RDF_L, double RDF_Sp, double RDF_Ns,
            double RDF_decid, double TS, double H_domvmi, double SC, double h, double SINCE_THINNING,
            int nres, double modelresult, char errors,
            int errorCheckMode, double allowedRiskLevel, double rectFactor) {

        int ret = 1;

        //Assign time since thinning dummy variables
        double THIN_0_5 = 0;
        if (SINCE_THINNING >= 0 && SINCE_THINNING <= 5) {
            THIN_0_5 = 1;
        }

        int SC1, SC2, SC4_8;
        int iSC = (int) SC;
        switch (iSC) {
            case 1:
                SC1 = 1;
                SC2 = 0;
                SC4_8 = 0;
                break;
            case 2:
                SC1 = 0;
                SC2 = 1;
                SC4_8 = 0;
                break;
            case 3:
                SC1 = 0;
                SC2 = 0;
                SC4_8 = 0;
                break;
            case 4:
                SC1 = 0;
                SC2 = 0;
                SC4_8 = 1;
                break;
            case 5:
                SC1 = 0;
                SC2 = 0;
                SC4_8 = 1;
                break;
            case 6:
                SC1 = 0;
                SC2 = 0;
                SC4_8 = 1;
                break;
            case 7:
                SC1 = 0;
                SC2 = 0;
                SC4_8 = 1;
                break;
            case 8:
                SC1 = 0;
                SC2 = 0;
                SC4_8 = 1;
                break;
            // defaults to SC==4, sensible?
            default:
                SC1 = 0;
                SC2 = 0;
                SC4_8 = 0;
                break;
        }

        //Check for fatal errors
        if (SI_50 <= 0) {
            ret = 0;

        }
        if (H_domvmi <= 0) {
            ret = 0;

        }
        if (d <= 0) {
            ret = 0;

        }
        if (cr <= 0) {
            ret = 0;

        }
        if (RDF_Sp < 0) {
            ret = 0;

        }
        if (RDF_Ns < 0) {
            ret = 0;

        }
        if (RDF_decid < 0) {
            ret = 0;

        }
        if (ret == 0) {

            return 0;
        }

        //To avoid extrapolation: problems caused by very little or large diameter minimum diameter for spruce is 1.1 cm and max 41 cm! (Hynynen ym.)
        if (d < 1.1) {
            d = 1.1;
        }
        if (d > 41) {
            d = 41;
        }
        if (H_domvmi < 4) {
            H_domvmi = 4;
        }

        nres = 1;
        double res = Math.exp(-5.08998 + 0.66849 * Math.log(SI_50) + 0.14526 * (SC1) + 0.11475 * SC2 - 0.10130 * SC4_8 + 7.10363 * (1 / Math.log(H_domvmi)) - 25.3565 * (1 / Math.pow(H_domvmi, 2))
                + 0.61162 * Math.log(d) - 0.00063 * Math.pow(d, 2) + 0.19257 * Math.pow(Math.log(d), 2) - 0.35418 * Math.log(cr) - 0.42997 * RDF_L - 0.67742 * Math.log(RDF_Sp + 1) - 0.78516 * Math.log(RDF_Ns + 1)
                - 0.30546 * Math.log(RDF_decid + 1) + 1.71107 * ((cr * TS) / 1000.0) + 0.11804 * THIN_0_5 + (Math.pow(0.2936, 2) + Math.pow(0.4709, 2)) / 2);

        if (h < 5) {
            if (res < 15) {
                res = -3 + 0.015 * TS;
            }
        }

        modelresult = res * rectFactor;
        return ret;

    }

    int Basal_area_growth_pine_Hynynenym(double d, double cr, double SI_50, double RDF_L, double RDF_Sp, double RDF_Ns,
            double RDF_decid, double TS, double H_domvmi, double SC, double h, double SINCE_THINNING,
            int nres, double modelresult, char errors,
            int errorCheckMode, double allowedRiskLevel, double rectFactor
    ) {

        int ret = 1;

        //Assign time since thinning dummy variables
        double THIN_0_5, THIN_5_10;
        THIN_0_5 = 0;
        THIN_5_10 = 0;
        if (SINCE_THINNING >= 0 && SINCE_THINNING <= 5) {
            THIN_0_5 = 1;
        }
        if (SINCE_THINNING > 5 && SINCE_THINNING <= 10) {
            THIN_5_10 = 1;
        }

        int SC1_2, SC3, SC5_8;
        int iSC = (int) SC;
        switch (iSC) {
            case 1:
                SC1_2 = 1;
                SC3 = 0;
                SC5_8 = 0;
                break;
            case 2:
                SC1_2 = 1;
                SC3 = 0;
                SC5_8 = 0;
                break;
            case 3:
                SC1_2 = 0;
                SC3 = 1;
                SC5_8 = 0;
                break;
            case 4:
                SC1_2 = 0;
                SC3 = 0;
                SC5_8 = 0;
                break;
            case 5:
                SC1_2 = 0;
                SC3 = 0;
                SC5_8 = 1;
                break;
            case 6:
                SC1_2 = 0;
                SC3 = 0;
                SC5_8 = 1;
                break;
            case 7:
                SC1_2 = 0;
                SC3 = 0;
                SC5_8 = 1;
                break;
            case 8:
                SC1_2 = 0;
                SC3 = 0;
                SC5_8 = 1;
                break;
            // defaults to SC==4, sensible?
            default:
                SC1_2 = 0;
                SC3 = 0;
                SC5_8 = 0;
                break;
        };

        //Check for fatal errors
        if (SI_50 <= 0) {
            ret = 0;

        }
        if (H_domvmi <= 0) {
            ret = 0;

        }
        if (d <= 0) {
            ret = 0;

        }
        if (cr <= 0) {
            ret = 0;

        }
        if (RDF_Sp < 0) {
            ret = 0;

        }
        if (RDF_Ns < 0) {
            ret = 0;

        }
        if (RDF_decid < 0) {
            ret = 0;

        }
        if (ret == 0) {

            return 0;
        }

        //To avoid extrapolation: problems caused by very little or large diameter minimum diameter for pine is 1.0 cm and max 29 cm! (Hynynen ym.)
        if (d < 1) {
            d = 1;
        }
        if (d > 29) {
            d = 29;
        }
        if (H_domvmi < 4) {
            H_domvmi = 4;
        }

        nres = 1;
        double res = Math.exp(-2.74332 + 0.75638 * Math.log(SI_50) + 0.11721 * SC1_2 + 0.05213 * SC3 - 0.13549 * SC5_8 + 6.46997 * (1 / H_domvmi) - 4.22774 * (1 / Math.pow(H_domvmi, 2))
                + 1.57123 * Math.log(d) - 0.00088 * Math.pow(d, 2) + 1.09423 * (1 / (d + 0.1) + 0.56858 * Math.log(cr) - 0.40647 * RDF_L - 0.89384 * Math.log(RDF_Sp + 1) - 0.97311 * Math.log(RDF_Ns + 1)
                - 0.94614 * Math.log(RDF_decid + 1) + 0.38051 * ((cr * TS) / 1000.0) + 0.11976 * THIN_0_5 + 0.0806 * THIN_5_10) + (Math.pow(0.2907, 2) + Math.pow(0.44, 2)) / 2);

        if (h < 5) {
            if (res < 15) {
                res = -3 + 0.015 * TS;
            }
        }

        modelresult = res * rectFactor;
        return ret;

    }

    /**
     * <p>
     * This is a simple description of the mklethod. . .
     * <a href="http://www.supermanisthegreatest.com">Superman!</a>
     * </p>
     *
     * @param incomingDamage the amount of incoming damage
     * @return the amount of health hero has after attack
     * @see <a href="http://www.link_to_jira/HERO-402">HERO-402</a>
     * @since 1.0
     */

    int Basal_area_growth_birch_Hynynenym(double d, double cr, double SI_50, double RDF_L, double RDF, double TS,
            double H_domvmi, double PLANT, double SP, double h, double SINCE_THINNING,
            int nres, double modelresult, char errors,
            int errorCheckMode, double allowedRiskLevel, double rectFactor) {

        int ret = 1;

        //Assign time since thinning dummy variables
        double THIN_0_10 = 0;
        if (SINCE_THINNING >= 0 && SINCE_THINNING <= 10) {
            THIN_0_10 = 1;
        }

        int PUB;
        PUB = 0;
        if (SP == 4) {
            PUB = 1;
        }

        //Check for fatal errors
        if (SI_50 <= 0) {
            ret = 0;

        }
        if (H_domvmi <= 0) {
            ret = 0;

        }
        if (d <= 0) {
            ret = 0;

        }
        if (cr <= 0) {
            ret = 0;

        }
        if (RDF <= 0) {
            ret = 0;

        }
        if (ret == 0) {

            return 0;
        }

        //To avoid extrapolation: problems caused by very little or large diameter minimum diameter for birch is 1.1 cm and max 40 cm! (Hynynen ym.)
        if (d < 1.1) {
            d = 1.1;
        }
        if (d > 40) {
            d = 40;
        }
        if (H_domvmi < 4) {
            H_domvmi = 4;
        }

        nres = 1;
        double res = Math.exp(-3.98207 + 0.76027 * Math.log(SI_50) + 14.97845 * (1 / H_domvmi) - 32.1554 * (1 / Math.pow(H_domvmi, 2)) + 1.314906 * Math.log(d + 1) - 0.00053 * Math.pow(d, 2)
                + 0.37444 * Math.log(cr) - 0.96221 * RDF_L - 0.59253 * Math.log(RDF + 1) + 1.16996 * (TS / 1000.0) + 0.36701 * PLANT + 0.19411 * THIN_0_10 - 0.16571 * PUB + (Math.pow(0.33827, 2) + Math.pow(0.50537, 2)) / 2);

        if (h < 5) {
            if (res < 15) {
                res = -3 + 0.015 * TS;
            }
        }

        modelresult = res * rectFactor;
        return ret;

    }

}
