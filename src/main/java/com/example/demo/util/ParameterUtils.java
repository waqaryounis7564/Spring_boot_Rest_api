package com.example.demo.util;




import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ParameterUtils {

    public static boolean isValidDateTime(String date) {
        if (date == null || date.isEmpty())
            return false;
        try {
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
            return true;
        } catch (ParseException ignored) {
            return false;
        }
    }

    public static boolean isValidNumberInString(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    public static boolean isValidDate(String date) {

        if (date == null || date.isEmpty())
            return false;
        try {
            new SimpleDateFormat("yyyy-MM-dd").parse(date);
            return true;
        } catch (ParseException ignored) {
            return false;
        }
    }

    public static boolean isValidExactDate(String date) {

        if (date == null || date.isEmpty())
            return false;
        try {
            if (date.length() == 10) {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                dateTimeFormatter.parse(date);
                return true;
            } else if (date.length() == 19) {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                dateTimeFormatter.parse(date);
                return true;
            } else
                return false;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static boolean isNullOrEmptyString(String value) {
        return value == null || value.isEmpty();
    }

    public static Timestamp getDateTime(String date) {
        Date dateFormat = null;
        try {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dateFormat != null)
            return new Timestamp(dateFormat.getTime());
        return null;
    }

    public static Timestamp getDateTimeAfterMinusDays(String date, int days) {
        Date dateFormat = null;
        try {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormat);
            calendar.add(Calendar.DAY_OF_MONTH, days);
            dateFormat = calendar.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dateFormat != null)
            return new Timestamp(dateFormat.getTime());
        return null;
    }

    public static Timestamp getDate(String date) {
        Date dateFormat = null;
        try {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dateFormat != null)
            return new Timestamp(dateFormat.getTime());
        return null;
    }

    public static int getValidIntegerFromString(String value) {
        if (value == null) return 0;
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException ignored) {
            return 0;
        }
    }

    public static boolean isNullOrInvalidInteger(Integer value) {
        return value == null;
    }

    public static String getDateFromDateMinusDays(Date date, int days) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -days);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }

    public static String getStringDateFromStringMinusDays(String date, int days) {

        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date));
        } catch (ParseException e) {
            return null;
        }
        calendar.add(Calendar.DAY_OF_MONTH, -days);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }

    public static Date getDateAfterMinusDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -days);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    public static String getDateInStringFromDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static Date getDateBeforeDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -days);
        return calendar.getTime();
    }

    public static Date getDateFromString(String date) {
        String patternDateTime = "yyyy-MM-dd HH:mm:ss";
        String patternDate = "yyyy-MM-dd";
        try {
            if (date.length() == patternDateTime.length())
                return new SimpleDateFormat(patternDateTime).parse(date);
            else if (date.length() == patternDate.length())
                return new SimpleDateFormat(patternDate).parse(date);
        } catch (ParseException ignored) {
        }
        return null;
    }

    public static String getDateInYourFormat(String positionDate, String thisDateFormat, String requiredDateFormat) {
        try {
            return new SimpleDateFormat(requiredDateFormat).format(new SimpleDateFormat(thisDateFormat).parse(positionDate));
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getDateInYourFormat(String positionDate, String[] thisDateFormat, String requiredDateFormat, Locale... locale) {
        boolean isLocalAvailable = Arrays.stream(locale).findFirst().isPresent();
        Locale local = null;
        if (isLocalAvailable)
            local = Arrays.stream(locale).findFirst().get();
        for (String format : thisDateFormat) {
            if (format.length() == positionDate.length()) {
                try {
                    if (local != null) {
                        return new SimpleDateFormat(requiredDateFormat).format(new SimpleDateFormat(format, local).parse(positionDate));
                    }
                    if (format.equals("HH:mm")) {
                        return new SimpleDateFormat("yyyy-MM-dd").format(new Date()).concat(" ").concat(positionDate);
                    }
                    return new SimpleDateFormat(requiredDateFormat).format(new SimpleDateFormat(format).parse(positionDate));
                } catch (ParseException ignored) {
                }
            }
        }
        return null;
    }

    public static boolean isDateBefore(Date theDate, Date beforeDate) {
        return theDate.before(beforeDate);
    }

    public static Date getParsedDate(String date, String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static void closeInputStream(InputStream inputStream) {
        if (inputStream != null)
            try {
                inputStream.close();
            } catch (IOException ignore) {
//                Logger logger = LoggerFactory.getLogger("Closing Stream Error");
//                logger.info(ignore.getMessage());
                System.out.println(ignore.getMessage());
            }
    }

    public static void closeOutputStream(OutputStream outputStream) {
        if (outputStream != null)
            try {
                outputStream.close();
            } catch (IOException ignore) {

            }
    }

    private static TrustManager[] getTrustMananger() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }
        };
    }

    public static SSLSocketFactory getSocketFactory() {
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, getTrustMananger(), new java.security.SecureRandom());
            return sc.getSocketFactory();
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


}
