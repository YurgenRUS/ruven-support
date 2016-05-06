package ru.kroshchenko.ruven.utils;

/**
 * @author Kroshchenko
 *         2016.01.19
 */
public class SurveyMagicId {

    private final static String sep = "Y";
    private final static Integer radix = 16;
    private final Integer companyId;
    private final Integer userId;
    private final Integer surveyId;
    private static final int ANSWER = 42;
    private static final int COOPER = 73;
    private static final int BRO = 83;

    public SurveyMagicId(Integer surveyId, Integer userId, Integer companyId) {
        if (surveyId != null && surveyId > 0
                && userId != null && userId > 0
                && companyId != null && companyId > 0) {
            this.companyId = companyId;
            this.userId = userId;
            this.surveyId = surveyId;
        } else {
            throw new IllegalArgumentException(String.format("{%s}, {%s}, {%s}", surveyId, userId, companyId));
        }

    }

    public SurveyMagicId(String value) {
        int companyId = 0;
        int userId = 0;
        int surveyId = 0;
        try {
            if (value != null && value.trim().length() >= 5) {
                String[] split = value.split(sep);
                if (split.length == 3) {
                    // c-u-s
                    companyId = (int) (Long.parseLong(split[0], radix) - 100) / ANSWER;
                    userId = (int) (Long.parseLong(split[1], radix) - 200) / COOPER;
                    surveyId = (int) (Long.parseLong(split[2], radix) - 400) / BRO;
                }
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(value);
        }
        if (companyId > 0 && userId > 0 && surveyId > 0) {
            this.companyId = companyId;
            this.userId = userId;
            this.surveyId = surveyId;
        } else {
            throw new IllegalArgumentException(value);
        }
    }

    public static String toMagicCode(Integer surveyId, Integer userId, Integer companyId) {
        if (surveyId != null && surveyId > 0
                && userId != null && userId > 0
                && companyId != null && userId > 0) {
            String cIdStr = Long.toHexString(companyId * ANSWER + 100);
            String uIdStr = Long.toHexString(userId * COOPER + 200);
            String sIdStr = Long.toHexString(surveyId * BRO + 400);
            return cIdStr + sep + uIdStr + sep + sIdStr;
        }
        return null;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getSurveyId() {
        return surveyId;
    }

    @Override
    public String toString() {
        return toMagicCode(surveyId, userId, companyId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SurveyMagicId other = (SurveyMagicId) obj;
        return this.toString().equals(other.toString());
    }

    @Override
    public int hashCode() {
        return toMagicCode(surveyId, userId, companyId).hashCode();
    }
}
