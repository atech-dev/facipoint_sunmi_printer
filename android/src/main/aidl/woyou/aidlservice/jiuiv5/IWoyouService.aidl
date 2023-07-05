//P, V series

package woyou.aidlservice.jiuiv5;

import woyou.aidlservice.jiuiv5.ICallback;
import woyou.aidlservice.jiuiv5.ITax;
import android.graphics.Bitmap;
import com.sunmi.trans.TransBean;

interface IWoyouService
{
    /**
    * Replace the original printer upgrade firmware interface (void updateFirmware())
    * Now changed to the data interface of the payload package name, only system calls
    * Supported version: above 4.0.0
    */
    boolean postPrintData(String packageName, in byte[] data, int offset, int length);

    /**
    * Printer firmware status
    * Return: 0--unknown, A5--bootloader, C3--print
    */
    int getFirmwareStatus();

    /**
    * Get print service version
    * Return: WoyouService service version
    */
    String getServiceVersion();

    /**
    * Initialize the printer, reset the logic program of the printer, but do not clear the buffer data, so
    * Incomplete print jobs will continue after reset
    */
    void printerInit(in ICallback callback);

    /**
    * Printer self-test, the printer will print a self-test page
    */
    void printerSelfChecking(in ICallback callback);

    /**
    * Get the serial number of the printer board
    * Returns: Serial number of the printer board
    */
    String getPrinterSerialNo();

    /**
    * Get the printer firmware version number
    * Return: Printer firmware version number
    */
    String getPrinterVersion();

    /**
    * Get the printer model
    * Return: Printer Model
    */
    String getPrinterModal();

    /**
    * Get the print length after the printer is powered on
    * Returned in callback onReturnString
    */
    void getPrintedLength(in ICallback callback);

    /**
    * The printer feeds the paper (forced line feed, and feeds n lines after finishing the previous print content)
    * n: the number of paper lines
    */
    void lineWrap(int n, in ICallback callback);

    /**
    * epson command print
    */
    void sendRAWData(in byte[] data, in ICallback callback);

    /**
    * Set the alignment mode, which will affect subsequent printing unless initialized
    * alignment: alignment 0--left, 1--center, 2--right
    */
    void setAlignment(int alignment, in ICallback callback);

    /**
    * Set the print font, temporarily only system calls are available, external calls are invalid
    */
    void setFontName(String typeface, in ICallback callback);

    /**
    * Set the font size, which will affect subsequent printing unless initialized
    *Note: Font size is beyond standard international instructions for printing,
    * Adjusting the font size will affect the character width, and the number of characters per line will also change,
    * Therefore, typography in monospaced fonts may be messed up
    * fontsize: font size
    */
    void setFontSize(float fontsize, in ICallback callback);

    /**
    * Print text, the text width will automatically wrap when one line is full, and if it is less than one line, it will not be printed unless forced to wrap
    * text: the text string to print
    */
    void printText(String text, in ICallback callback);

    /**
    * Print the text of the specified font, the font setting is only valid for this time
    * text: the text to print
    * typeface: font name (temporarily only system calls, external calls are invalid)
    * fontsize: font size
    */
    void printTextWithFont(String text, String typeface, float fontsize, in ICallback callback);

    /**
    * Print a row of the table, you can specify the column width and alignment
    * colsTextArr: Array of text strings for each column
    * colsWidthArr: Array of the width of each column (calculated in English characters, each Chinese character occupies two English characters, each width is greater than 0)
    * colsAlign: Alignment of each column (0 to the left, 1 to the center, 2 to the right)
    * Remark: The array length of the three parameters should be the same, if the width of colsText[i] is greater than colsWidth[i], the text wraps
    */
    void printColumnsText(in String[] colsTextArr, in int[] colsWidthArr, in int[] colsAlign, in ICallback callback);

    /**
    * print pictures
    * bitmap: the maximum width is 384 pixels, if the width is exceeded, the display will be incomplete; the image size length * width <8M;
    */
    void printBitmap(in Bitmap bitmap, in ICallback callback);

    /**
    * Print 1D barcode
    * data: barcode data
    * symbology: barcode type
    * 0 -- UPC-A, requires 12 digits (the last check digit must be correct), but is limited by the width of the printer and the width of the barcode
    * 1 -- UPC-E, requires 8 digits (the last check digit must be correct), but is limited by the width of the printer and the width of the barcode
    * 2 -- JAN13(EAN13), requires 13 digits (the last check digit must be correct), but is limited by the width of the printer and the width of the barcode
    * 3 -- JAN8(EAN8), requires 8 digits (the last check digit must be correct), but is limited by the width of the printer and the width of the barcode
    * 4 -- CODE39, numeric English and 8 special symbols with * at the beginning and end, but limited by the width of the printer and the width of the barcode
    * 5 -- ITF, the characters are numbers and less than 14 digits, but limited by the width of the printer and the width of the barcode
    * 6 -- CODABAR, the start and end must be A-D, the data is 0-9 and 6 special characters, the length is arbitrary but limited by the width of the printer and the width of the barcode
    * 7 -- CODE93, the character is arbitrary, the length is arbitrary but limited by the width of the printer and the width of the barcode
    * 8 -- CODE128 characters are arbitrary, the length is arbitrary but limited by the width of the printer and the width of the barcode
    * height: bar code height, value from 1 to 255, default 162
    * width: barcode width, value 2 to 6, default 2
    * textposition: text position 0--do not print text, 1--text above the barcode, 2--text below the barcode, 3--print both above and below the barcode
    */
    void printBarCode(String data, int symbology, int height, int width, int textposition, in ICallback callback);

    /**
    * Print 2D barcode
    * data: QR code data
    * modulesize: QR code block size (unit: point, value from 1 to 16 )
    * errorlevel: QR code error correction level (0 to 3),
    * 0 -- error correction level L (7%),
    * 1 -- error correction level M (15%),
    * 2 -- error correction level Q (25%),
    * 3 -- Error correction level H (30%)
    */
    void printQRCode(String data, int modulesize, int errorlevel, in ICallback callback);

    /**
    * Print text, the text width will automatically wrap when one line is full, and if it is less than one line, it will not be printed unless forced to wrap
    * The text is output as the width of the vector text, that is, each character is not of equal width
    * text: the text string to print
    */
    void printOriginalText(String text, in ICallback callback);

    /**
    * lib package printing dedicated interface
    * transbean: print task list
    */
    void commitPrint(in TransBean[] transbean, in ICallback callback);

    /**
    * print buffer content
    */
    void commitPrinterBuffer();

    /**
    * Enter transaction mode, all print calls will be cached;
    * Call commitPrinterBuffer(), exitPrinterBuffer(true), commitPrinterBufferWithCallback(),
    * print after exitPrinterBufferWithCallback(true);
    * clean: whether to clear the cached buffer content if the transaction mode has not been exited before
    */
    void enterPrinterBuffer(in boolean clean);

    /**
    * Exit buffer mode
    * commit: whether to print out the buffer content
    */
    void exitPrinterBuffer(in boolean commit);

    /**
    * Send NC commands
    * data: tax control order
    */
    void tax(in byte[] data,in ITax callback);

    /**
    * Get the model of the printer head
    * Returned in callback onReturnString
    */
    void getPrinterFactory(in ICallback callback);

    /**
    * Clear printer cache data (only system calls, external calls are invalid)
    */
    void clearBuffer();

    /**
    * Print buffer content with feedback
    */
    void commitPrinterBufferWithCallback(in ICallback callback);

    /**
    * Exit buffer print mode with feedback
    * commit: whether to commit the buffer content
    */
    void exitPrinterBufferWithCallback(in boolean commit, in ICallback callback);

    /**
    * Print a row of the table, you can specify the column width and alignment
    * colsTextArr: Array of text strings for each column
    * colsWidthArr: The width weight of each column is the proportion of each column
    * colsAlign: Alignment of each column (0 to the left, 1 to the center, 2 to the right)
    * Remark: The array length of the three parameters should be the same, if the width of colsText[i] is greater than colsWidth[i], the text wraps
    */
    void printColumnsString(in String[] colsTextArr, in int[] colsWidthArr, in int[] colsAlign, in ICallback callback);

    /**
    * Get the latest status of the printer
    * Return: printer status feedback 1 normal 2 in preparation 3 communication abnormal 4 out of paper 5 overheated 505: no printer 507: update failed
    */

    int updatePrinterState();

    /**
    * Custom print pictures
    * bitmap: image bitmap object (maximum width is 384 pixels, if the image exceeds 1M, it cannot be printed)
    * type: There are currently two printing methods: 0, same as printBitmap 1, black and white image with a threshold of 200 2, grayscale image
    * Supported version: P1-v3.2.0 and above
    * P14g-v1.1.6 and above
    * V1s-v3.1.6 and above
    * Above V2-v1.0.0
    */
    void printBitmapCustom(in Bitmap bitmap, in int type, in ICallback callback);

    /**
    * Get the force-enabled font doubling state
    * return 0: not enabled 1: double width 2: double height 3: double height and double width
    * Supported version: P1-v3.2.0 and above
    * P14g-v1.2.0 and above
    * Above V1s-v3.2.0
    * Above V2-v1.0.0
    */
    int getForcedDouble();

    /**
    * Whether to force the anti-white style to be enabled
    * return true: enabled false: not enabled
    * Supported version: P1-v3.2.0 and above
    * P14g-v1.2.0 and above
    * Above V1s-v3.2.0
    * Above V2-v1.0.0
    */
    boolean isForcedAntiWhite();

    /**
    * Whether to force the bold style to be enabled
    * return true: enabled false: not enabled
    * Supported version: P1-v3.2.0 and above
    * P14g-v1.2.0 and above
    * Above V1s-v3.2.0
    * Above V2-v1.0.0
    */
    boolean isForcedBold();

    /**
    * Whether to force the underline style to be enabled
    * return true: enabled false: not enabled
    * Supported version: P1-v3.2.0 and above
    * P14g-v1.2.0 and above
    * Above V1s-v3.2.0
    * Above V2-v1.0.0
    */
    boolean isForcedUnderline();

    /**
    * Get force-enable row-height status
    * return -1: not enabled 0~255: force line height pixel height
    * Supported version: P1-v3.2.0 and above
    * P14g-v1.2.0 and above
    * Above V1s-v3.2.0
    * Above V2-v1.0.0
    */
    int getForcedRowHeight();

    /**
    * Get the current font
    * Return 0: Sunmi Font 1.0 1: Sunmi Font 2.0
    * Supported version: P1-v3.2.0 and above
    * P14g-v1.2.0 and above
    * Above V1s-v3.2.0
    * Above V2-v1.0.0
    */
    int getFontName();
}