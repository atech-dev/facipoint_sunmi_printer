/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package woyou.aidlservice.jiuiv5;
public interface IWoyouService extends android.os.IInterface
{
  /** Default implementation for IWoyouService. */
  public static class Default implements woyou.aidlservice.jiuiv5.IWoyouService
  {
    /**
        * Replace the original printer upgrade firmware interface (void updateFirmware())
        * Now changed to the data interface of the payload package name, only system calls
        * Supported version: above 4.0.0
        */
    @Override public boolean postPrintData(java.lang.String packageName, byte[] data, int offset, int length) throws android.os.RemoteException
    {
      return false;
    }
    /**
        * Printer firmware status
        * Return: 0--unknown, A5--bootloader, C3--print
        */
    @Override public int getFirmwareStatus() throws android.os.RemoteException
    {
      return 0;
    }
    /**
        * Get print service version
        * Return: WoyouService service version
        */
    @Override public java.lang.String getServiceVersion() throws android.os.RemoteException
    {
      return null;
    }
    /**
        * Initialize the printer, reset the logic program of the printer, but do not clear the buffer data, so
        * Incomplete print jobs will continue after reset
        */
    @Override public void printerInit(woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
    {
    }
    /**
        * Printer self-test, the printer will print a self-test page
        */
    @Override public void printerSelfChecking(woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
    {
    }
    /**
        * Get the serial number of the printer board
        * Returns: Serial number of the printer board
        */
    @Override public java.lang.String getPrinterSerialNo() throws android.os.RemoteException
    {
      return null;
    }
    /**
        * Get the printer firmware version number
        * Return: Printer firmware version number
        */
    @Override public java.lang.String getPrinterVersion() throws android.os.RemoteException
    {
      return null;
    }
    /**
        * Get the printer model
        * Return: Printer Model
        */
    @Override public java.lang.String getPrinterModal() throws android.os.RemoteException
    {
      return null;
    }
    /**
        * Get the print length after the printer is powered on
        * Returned in callback onReturnString
        */
    @Override public void getPrintedLength(woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
    {
    }
    /**
        * The printer feeds the paper (forced line feed, and feeds n lines after finishing the previous print content)
        * n: the number of paper lines
        */
    @Override public void lineWrap(int n, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
    {
    }
    /**
        * epson command print
        */
    @Override public void sendRAWData(byte[] data, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
    {
    }
    /**
        * Set the alignment mode, which will affect subsequent printing unless initialized
        * alignment: alignment 0--left, 1--center, 2--right
        */
    @Override public void setAlignment(int alignment, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
    {
    }
    /**
        * Set the print font, temporarily only system calls are available, external calls are invalid
        */
    @Override public void setFontName(java.lang.String typeface, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
    {
    }
    /**
        * Set the font size, which will affect subsequent printing unless initialized
        *Note: Font size is beyond standard international instructions for printing,
        * Adjusting the font size will affect the character width, and the number of characters per line will also change,
        * Therefore, typography in monospaced fonts may be messed up
        * fontsize: font size
        */
    @Override public void setFontSize(float fontsize, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
    {
    }
    /**
        * Print text, the text width will automatically wrap when one line is full, and if it is less than one line, it will not be printed unless forced to wrap
        * text: the text string to print
        */
    @Override public void printText(java.lang.String text, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
    {
    }
    /**
        * Print the text of the specified font, the font setting is only valid for this time
        * text: the text to print
        * typeface: font name (temporarily only system calls, external calls are invalid)
        * fontsize: font size
        */
    @Override public void printTextWithFont(java.lang.String text, java.lang.String typeface, float fontsize, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
    {
    }
    /**
        * Print a row of the table, you can specify the column width and alignment
        * colsTextArr: Array of text strings for each column
        * colsWidthArr: Array of the width of each column (calculated in English characters, each Chinese character occupies two English characters, each width is greater than 0)
        * colsAlign: Alignment of each column (0 to the left, 1 to the center, 2 to the right)
        * Remark: The array length of the three parameters should be the same, if the width of colsText[i] is greater than colsWidth[i], the text wraps
        */
    @Override public void printColumnsText(java.lang.String[] colsTextArr, int[] colsWidthArr, int[] colsAlign, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
    {
    }
    /**
        * print pictures
        * bitmap: the maximum width is 384 pixels, if the width is exceeded, the display will be incomplete; the image size length * width <8M;
        */
    @Override public void printBitmap(android.graphics.Bitmap bitmap, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
    {
    }
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
    @Override public void printBarCode(java.lang.String data, int symbology, int height, int width, int textposition, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
    {
    }
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
    @Override public void printQRCode(java.lang.String data, int modulesize, int errorlevel, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
    {
    }
    /**
        * Print text, the text width will automatically wrap when one line is full, and if it is less than one line, it will not be printed unless forced to wrap
        * The text is output as the width of the vector text, that is, each character is not of equal width
        * text: the text string to print
        */
    @Override public void printOriginalText(java.lang.String text, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
    {
    }
    /**
        * lib package printing dedicated interface
        * transbean: print task list
        */
    @Override public void commitPrint(com.sunmi.trans.TransBean[] transbean, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
    {
    }
    /**
        * print buffer content
        */
    @Override public void commitPrinterBuffer() throws android.os.RemoteException
    {
    }
    /**
        * Enter transaction mode, all print calls will be cached;
        * Call commitPrinterBuffer(), exitPrinterBuffer(true), commitPrinterBufferWithCallback(),
        * print after exitPrinterBufferWithCallback(true);
        * clean: whether to clear the cached buffer content if the transaction mode has not been exited before
        */
    @Override public void enterPrinterBuffer(boolean clean) throws android.os.RemoteException
    {
    }
    /**
        * Exit buffer mode
        * commit: whether to print out the buffer content
        */
    @Override public void exitPrinterBuffer(boolean commit) throws android.os.RemoteException
    {
    }
    /**
        * Send NC commands
        * data: tax control order
        */
    @Override public void tax(byte[] data, woyou.aidlservice.jiuiv5.ITax callback) throws android.os.RemoteException
    {
    }
    /**
        * Get the model of the printer head
        * Returned in callback onReturnString
        */
    @Override public void getPrinterFactory(woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
    {
    }
    /**
        * Clear printer cache data (only system calls, external calls are invalid)
        */
    @Override public void clearBuffer() throws android.os.RemoteException
    {
    }
    /**
        * Print buffer content with feedback
        */
    @Override public void commitPrinterBufferWithCallback(woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
    {
    }
    /**
        * Exit buffer print mode with feedback
        * commit: whether to commit the buffer content
        */
    @Override public void exitPrinterBufferWithCallback(boolean commit, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
    {
    }
    /**
        * Print a row of the table, you can specify the column width and alignment
        * colsTextArr: Array of text strings for each column
        * colsWidthArr: The width weight of each column is the proportion of each column
        * colsAlign: Alignment of each column (0 to the left, 1 to the center, 2 to the right)
        * Remark: The array length of the three parameters should be the same, if the width of colsText[i] is greater than colsWidth[i], the text wraps
        */
    @Override public void printColumnsString(java.lang.String[] colsTextArr, int[] colsWidthArr, int[] colsAlign, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
    {
    }
    /**
        * Get the latest status of the printer
        * Return: printer status feedback 1 normal 2 in preparation 3 communication abnormal 4 out of paper 5 overheated 505: no printer 507: update failed
        */
    @Override public int updatePrinterState() throws android.os.RemoteException
    {
      return 0;
    }
    /**
        * Custom print pictures
        * bitmap: image bitmap object (maximum width is 384 pixels, if the image exceeds 1M, it cannot be printed)
        * type: There are currently two printing methods: 0, same as printBitmap 1, black and white image with a threshold of 200 2, grayscale image
        * Supported version: P1-v3.2.0 and above
        * P14g-v1.1.6 and above
        * V1s-v3.1.6 and above
        * Above V2-v1.0.0
        */
    @Override public void printBitmapCustom(android.graphics.Bitmap bitmap, int type, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
    {
    }
    /**
        * Get the force-enabled font doubling state
        * return 0: not enabled 1: double width 2: double height 3: double height and double width
        * Supported version: P1-v3.2.0 and above
        * P14g-v1.2.0 and above
        * Above V1s-v3.2.0
        * Above V2-v1.0.0
        */
    @Override public int getForcedDouble() throws android.os.RemoteException
    {
      return 0;
    }
    /**
        * Whether to force the anti-white style to be enabled
        * return true: enabled false: not enabled
        * Supported version: P1-v3.2.0 and above
        * P14g-v1.2.0 and above
        * Above V1s-v3.2.0
        * Above V2-v1.0.0
        */
    @Override public boolean isForcedAntiWhite() throws android.os.RemoteException
    {
      return false;
    }
    /**
        * Whether to force the bold style to be enabled
        * return true: enabled false: not enabled
        * Supported version: P1-v3.2.0 and above
        * P14g-v1.2.0 and above
        * Above V1s-v3.2.0
        * Above V2-v1.0.0
        */
    @Override public boolean isForcedBold() throws android.os.RemoteException
    {
      return false;
    }
    /**
        * Whether to force the underline style to be enabled
        * return true: enabled false: not enabled
        * Supported version: P1-v3.2.0 and above
        * P14g-v1.2.0 and above
        * Above V1s-v3.2.0
        * Above V2-v1.0.0
        */
    @Override public boolean isForcedUnderline() throws android.os.RemoteException
    {
      return false;
    }
    /**
        * Get force-enable row-height status
        * return -1: not enabled 0~255: force line height pixel height
        * Supported version: P1-v3.2.0 and above
        * P14g-v1.2.0 and above
        * Above V1s-v3.2.0
        * Above V2-v1.0.0
        */
    @Override public int getForcedRowHeight() throws android.os.RemoteException
    {
      return 0;
    }
    /**
        * Get the current font
        * Return 0: Sunmi Font 1.0 1: Sunmi Font 2.0
        * Supported version: P1-v3.2.0 and above
        * P14g-v1.2.0 and above
        * Above V1s-v3.2.0
        * Above V2-v1.0.0
        */
    @Override public int getFontName() throws android.os.RemoteException
    {
      return 0;
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements woyou.aidlservice.jiuiv5.IWoyouService
  {
    private static final java.lang.String DESCRIPTOR = "woyou.aidlservice.jiuiv5.IWoyouService";
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an woyou.aidlservice.jiuiv5.IWoyouService interface,
     * generating a proxy if needed.
     */
    public static woyou.aidlservice.jiuiv5.IWoyouService asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof woyou.aidlservice.jiuiv5.IWoyouService))) {
        return ((woyou.aidlservice.jiuiv5.IWoyouService)iin);
      }
      return new woyou.aidlservice.jiuiv5.IWoyouService.Stub.Proxy(obj);
    }
    @Override public android.os.IBinder asBinder()
    {
      return this;
    }
    @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
    {
      java.lang.String descriptor = DESCRIPTOR;
      switch (code)
      {
        case INTERFACE_TRANSACTION:
        {
          reply.writeString(descriptor);
          return true;
        }
        case TRANSACTION_postPrintData:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          byte[] _arg1;
          _arg1 = data.createByteArray();
          int _arg2;
          _arg2 = data.readInt();
          int _arg3;
          _arg3 = data.readInt();
          boolean _result = this.postPrintData(_arg0, _arg1, _arg2, _arg3);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_getFirmwareStatus:
        {
          data.enforceInterface(descriptor);
          int _result = this.getFirmwareStatus();
          reply.writeNoException();
          reply.writeInt(_result);
          return true;
        }
        case TRANSACTION_getServiceVersion:
        {
          data.enforceInterface(descriptor);
          java.lang.String _result = this.getServiceVersion();
          reply.writeNoException();
          reply.writeString(_result);
          return true;
        }
        case TRANSACTION_printerInit:
        {
          data.enforceInterface(descriptor);
          woyou.aidlservice.jiuiv5.ICallback _arg0;
          _arg0 = woyou.aidlservice.jiuiv5.ICallback.Stub.asInterface(data.readStrongBinder());
          this.printerInit(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_printerSelfChecking:
        {
          data.enforceInterface(descriptor);
          woyou.aidlservice.jiuiv5.ICallback _arg0;
          _arg0 = woyou.aidlservice.jiuiv5.ICallback.Stub.asInterface(data.readStrongBinder());
          this.printerSelfChecking(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_getPrinterSerialNo:
        {
          data.enforceInterface(descriptor);
          java.lang.String _result = this.getPrinterSerialNo();
          reply.writeNoException();
          reply.writeString(_result);
          return true;
        }
        case TRANSACTION_getPrinterVersion:
        {
          data.enforceInterface(descriptor);
          java.lang.String _result = this.getPrinterVersion();
          reply.writeNoException();
          reply.writeString(_result);
          return true;
        }
        case TRANSACTION_getPrinterModal:
        {
          data.enforceInterface(descriptor);
          java.lang.String _result = this.getPrinterModal();
          reply.writeNoException();
          reply.writeString(_result);
          return true;
        }
        case TRANSACTION_getPrintedLength:
        {
          data.enforceInterface(descriptor);
          woyou.aidlservice.jiuiv5.ICallback _arg0;
          _arg0 = woyou.aidlservice.jiuiv5.ICallback.Stub.asInterface(data.readStrongBinder());
          this.getPrintedLength(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_lineWrap:
        {
          data.enforceInterface(descriptor);
          int _arg0;
          _arg0 = data.readInt();
          woyou.aidlservice.jiuiv5.ICallback _arg1;
          _arg1 = woyou.aidlservice.jiuiv5.ICallback.Stub.asInterface(data.readStrongBinder());
          this.lineWrap(_arg0, _arg1);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_sendRAWData:
        {
          data.enforceInterface(descriptor);
          byte[] _arg0;
          _arg0 = data.createByteArray();
          woyou.aidlservice.jiuiv5.ICallback _arg1;
          _arg1 = woyou.aidlservice.jiuiv5.ICallback.Stub.asInterface(data.readStrongBinder());
          this.sendRAWData(_arg0, _arg1);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_setAlignment:
        {
          data.enforceInterface(descriptor);
          int _arg0;
          _arg0 = data.readInt();
          woyou.aidlservice.jiuiv5.ICallback _arg1;
          _arg1 = woyou.aidlservice.jiuiv5.ICallback.Stub.asInterface(data.readStrongBinder());
          this.setAlignment(_arg0, _arg1);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_setFontName:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          woyou.aidlservice.jiuiv5.ICallback _arg1;
          _arg1 = woyou.aidlservice.jiuiv5.ICallback.Stub.asInterface(data.readStrongBinder());
          this.setFontName(_arg0, _arg1);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_setFontSize:
        {
          data.enforceInterface(descriptor);
          float _arg0;
          _arg0 = data.readFloat();
          woyou.aidlservice.jiuiv5.ICallback _arg1;
          _arg1 = woyou.aidlservice.jiuiv5.ICallback.Stub.asInterface(data.readStrongBinder());
          this.setFontSize(_arg0, _arg1);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_printText:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          woyou.aidlservice.jiuiv5.ICallback _arg1;
          _arg1 = woyou.aidlservice.jiuiv5.ICallback.Stub.asInterface(data.readStrongBinder());
          this.printText(_arg0, _arg1);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_printTextWithFont:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          java.lang.String _arg1;
          _arg1 = data.readString();
          float _arg2;
          _arg2 = data.readFloat();
          woyou.aidlservice.jiuiv5.ICallback _arg3;
          _arg3 = woyou.aidlservice.jiuiv5.ICallback.Stub.asInterface(data.readStrongBinder());
          this.printTextWithFont(_arg0, _arg1, _arg2, _arg3);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_printColumnsText:
        {
          data.enforceInterface(descriptor);
          java.lang.String[] _arg0;
          _arg0 = data.createStringArray();
          int[] _arg1;
          _arg1 = data.createIntArray();
          int[] _arg2;
          _arg2 = data.createIntArray();
          woyou.aidlservice.jiuiv5.ICallback _arg3;
          _arg3 = woyou.aidlservice.jiuiv5.ICallback.Stub.asInterface(data.readStrongBinder());
          this.printColumnsText(_arg0, _arg1, _arg2, _arg3);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_printBitmap:
        {
          data.enforceInterface(descriptor);
          android.graphics.Bitmap _arg0;
          if ((0!=data.readInt())) {
            _arg0 = android.graphics.Bitmap.CREATOR.createFromParcel(data);
          }
          else {
            _arg0 = null;
          }
          woyou.aidlservice.jiuiv5.ICallback _arg1;
          _arg1 = woyou.aidlservice.jiuiv5.ICallback.Stub.asInterface(data.readStrongBinder());
          this.printBitmap(_arg0, _arg1);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_printBarCode:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          int _arg1;
          _arg1 = data.readInt();
          int _arg2;
          _arg2 = data.readInt();
          int _arg3;
          _arg3 = data.readInt();
          int _arg4;
          _arg4 = data.readInt();
          woyou.aidlservice.jiuiv5.ICallback _arg5;
          _arg5 = woyou.aidlservice.jiuiv5.ICallback.Stub.asInterface(data.readStrongBinder());
          this.printBarCode(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_printQRCode:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          int _arg1;
          _arg1 = data.readInt();
          int _arg2;
          _arg2 = data.readInt();
          woyou.aidlservice.jiuiv5.ICallback _arg3;
          _arg3 = woyou.aidlservice.jiuiv5.ICallback.Stub.asInterface(data.readStrongBinder());
          this.printQRCode(_arg0, _arg1, _arg2, _arg3);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_printOriginalText:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          woyou.aidlservice.jiuiv5.ICallback _arg1;
          _arg1 = woyou.aidlservice.jiuiv5.ICallback.Stub.asInterface(data.readStrongBinder());
          this.printOriginalText(_arg0, _arg1);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_commitPrint:
        {
          data.enforceInterface(descriptor);
          com.sunmi.trans.TransBean[] _arg0;
          _arg0 = data.createTypedArray(com.sunmi.trans.TransBean.CREATOR);
          woyou.aidlservice.jiuiv5.ICallback _arg1;
          _arg1 = woyou.aidlservice.jiuiv5.ICallback.Stub.asInterface(data.readStrongBinder());
          this.commitPrint(_arg0, _arg1);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_commitPrinterBuffer:
        {
          data.enforceInterface(descriptor);
          this.commitPrinterBuffer();
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_enterPrinterBuffer:
        {
          data.enforceInterface(descriptor);
          boolean _arg0;
          _arg0 = (0!=data.readInt());
          this.enterPrinterBuffer(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_exitPrinterBuffer:
        {
          data.enforceInterface(descriptor);
          boolean _arg0;
          _arg0 = (0!=data.readInt());
          this.exitPrinterBuffer(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_tax:
        {
          data.enforceInterface(descriptor);
          byte[] _arg0;
          _arg0 = data.createByteArray();
          woyou.aidlservice.jiuiv5.ITax _arg1;
          _arg1 = woyou.aidlservice.jiuiv5.ITax.Stub.asInterface(data.readStrongBinder());
          this.tax(_arg0, _arg1);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_getPrinterFactory:
        {
          data.enforceInterface(descriptor);
          woyou.aidlservice.jiuiv5.ICallback _arg0;
          _arg0 = woyou.aidlservice.jiuiv5.ICallback.Stub.asInterface(data.readStrongBinder());
          this.getPrinterFactory(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_clearBuffer:
        {
          data.enforceInterface(descriptor);
          this.clearBuffer();
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_commitPrinterBufferWithCallback:
        {
          data.enforceInterface(descriptor);
          woyou.aidlservice.jiuiv5.ICallback _arg0;
          _arg0 = woyou.aidlservice.jiuiv5.ICallback.Stub.asInterface(data.readStrongBinder());
          this.commitPrinterBufferWithCallback(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_exitPrinterBufferWithCallback:
        {
          data.enforceInterface(descriptor);
          boolean _arg0;
          _arg0 = (0!=data.readInt());
          woyou.aidlservice.jiuiv5.ICallback _arg1;
          _arg1 = woyou.aidlservice.jiuiv5.ICallback.Stub.asInterface(data.readStrongBinder());
          this.exitPrinterBufferWithCallback(_arg0, _arg1);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_printColumnsString:
        {
          data.enforceInterface(descriptor);
          java.lang.String[] _arg0;
          _arg0 = data.createStringArray();
          int[] _arg1;
          _arg1 = data.createIntArray();
          int[] _arg2;
          _arg2 = data.createIntArray();
          woyou.aidlservice.jiuiv5.ICallback _arg3;
          _arg3 = woyou.aidlservice.jiuiv5.ICallback.Stub.asInterface(data.readStrongBinder());
          this.printColumnsString(_arg0, _arg1, _arg2, _arg3);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_updatePrinterState:
        {
          data.enforceInterface(descriptor);
          int _result = this.updatePrinterState();
          reply.writeNoException();
          reply.writeInt(_result);
          return true;
        }
        case TRANSACTION_printBitmapCustom:
        {
          data.enforceInterface(descriptor);
          android.graphics.Bitmap _arg0;
          if ((0!=data.readInt())) {
            _arg0 = android.graphics.Bitmap.CREATOR.createFromParcel(data);
          }
          else {
            _arg0 = null;
          }
          int _arg1;
          _arg1 = data.readInt();
          woyou.aidlservice.jiuiv5.ICallback _arg2;
          _arg2 = woyou.aidlservice.jiuiv5.ICallback.Stub.asInterface(data.readStrongBinder());
          this.printBitmapCustom(_arg0, _arg1, _arg2);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_getForcedDouble:
        {
          data.enforceInterface(descriptor);
          int _result = this.getForcedDouble();
          reply.writeNoException();
          reply.writeInt(_result);
          return true;
        }
        case TRANSACTION_isForcedAntiWhite:
        {
          data.enforceInterface(descriptor);
          boolean _result = this.isForcedAntiWhite();
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_isForcedBold:
        {
          data.enforceInterface(descriptor);
          boolean _result = this.isForcedBold();
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_isForcedUnderline:
        {
          data.enforceInterface(descriptor);
          boolean _result = this.isForcedUnderline();
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_getForcedRowHeight:
        {
          data.enforceInterface(descriptor);
          int _result = this.getForcedRowHeight();
          reply.writeNoException();
          reply.writeInt(_result);
          return true;
        }
        case TRANSACTION_getFontName:
        {
          data.enforceInterface(descriptor);
          int _result = this.getFontName();
          reply.writeNoException();
          reply.writeInt(_result);
          return true;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
    }
    private static class Proxy implements woyou.aidlservice.jiuiv5.IWoyouService
    {
      private android.os.IBinder mRemote;
      Proxy(android.os.IBinder remote)
      {
        mRemote = remote;
      }
      @Override public android.os.IBinder asBinder()
      {
        return mRemote;
      }
      public java.lang.String getInterfaceDescriptor()
      {
        return DESCRIPTOR;
      }
      /**
          * Replace the original printer upgrade firmware interface (void updateFirmware())
          * Now changed to the data interface of the payload package name, only system calls
          * Supported version: above 4.0.0
          */
      @Override public boolean postPrintData(java.lang.String packageName, byte[] data, int offset, int length) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeByteArray(data);
          _data.writeInt(offset);
          _data.writeInt(length);
          boolean _status = mRemote.transact(Stub.TRANSACTION_postPrintData, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().postPrintData(packageName, data, offset, length);
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
          * Printer firmware status
          * Return: 0--unknown, A5--bootloader, C3--print
          */
      @Override public int getFirmwareStatus() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getFirmwareStatus, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getFirmwareStatus();
          }
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
          * Get print service version
          * Return: WoyouService service version
          */
      @Override public java.lang.String getServiceVersion() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getServiceVersion, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getServiceVersion();
          }
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
          * Initialize the printer, reset the logic program of the printer, but do not clear the buffer data, so
          * Incomplete print jobs will continue after reset
          */
      @Override public void printerInit(woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_printerInit, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().printerInit(callback);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
          * Printer self-test, the printer will print a self-test page
          */
      @Override public void printerSelfChecking(woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_printerSelfChecking, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().printerSelfChecking(callback);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
          * Get the serial number of the printer board
          * Returns: Serial number of the printer board
          */
      @Override public java.lang.String getPrinterSerialNo() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getPrinterSerialNo, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getPrinterSerialNo();
          }
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
          * Get the printer firmware version number
          * Return: Printer firmware version number
          */
      @Override public java.lang.String getPrinterVersion() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getPrinterVersion, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getPrinterVersion();
          }
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
          * Get the printer model
          * Return: Printer Model
          */
      @Override public java.lang.String getPrinterModal() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getPrinterModal, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getPrinterModal();
          }
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
          * Get the print length after the printer is powered on
          * Returned in callback onReturnString
          */
      @Override public void getPrintedLength(woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_getPrintedLength, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().getPrintedLength(callback);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
          * The printer feeds the paper (forced line feed, and feeds n lines after finishing the previous print content)
          * n: the number of paper lines
          */
      @Override public void lineWrap(int n, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(n);
          _data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_lineWrap, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().lineWrap(n, callback);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
          * epson command print
          */
      @Override public void sendRAWData(byte[] data, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeByteArray(data);
          _data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_sendRAWData, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().sendRAWData(data, callback);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
          * Set the alignment mode, which will affect subsequent printing unless initialized
          * alignment: alignment 0--left, 1--center, 2--right
          */
      @Override public void setAlignment(int alignment, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(alignment);
          _data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_setAlignment, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().setAlignment(alignment, callback);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
          * Set the print font, temporarily only system calls are available, external calls are invalid
          */
      @Override public void setFontName(java.lang.String typeface, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(typeface);
          _data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_setFontName, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().setFontName(typeface, callback);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
          * Set the font size, which will affect subsequent printing unless initialized
          *Note: Font size is beyond standard international instructions for printing,
          * Adjusting the font size will affect the character width, and the number of characters per line will also change,
          * Therefore, typography in monospaced fonts may be messed up
          * fontsize: font size
          */
      @Override public void setFontSize(float fontsize, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeFloat(fontsize);
          _data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_setFontSize, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().setFontSize(fontsize, callback);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
          * Print text, the text width will automatically wrap when one line is full, and if it is less than one line, it will not be printed unless forced to wrap
          * text: the text string to print
          */
      @Override public void printText(java.lang.String text, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(text);
          _data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_printText, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().printText(text, callback);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
          * Print the text of the specified font, the font setting is only valid for this time
          * text: the text to print
          * typeface: font name (temporarily only system calls, external calls are invalid)
          * fontsize: font size
          */
      @Override public void printTextWithFont(java.lang.String text, java.lang.String typeface, float fontsize, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(text);
          _data.writeString(typeface);
          _data.writeFloat(fontsize);
          _data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_printTextWithFont, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().printTextWithFont(text, typeface, fontsize, callback);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
          * Print a row of the table, you can specify the column width and alignment
          * colsTextArr: Array of text strings for each column
          * colsWidthArr: Array of the width of each column (calculated in English characters, each Chinese character occupies two English characters, each width is greater than 0)
          * colsAlign: Alignment of each column (0 to the left, 1 to the center, 2 to the right)
          * Remark: The array length of the three parameters should be the same, if the width of colsText[i] is greater than colsWidth[i], the text wraps
          */
      @Override public void printColumnsText(java.lang.String[] colsTextArr, int[] colsWidthArr, int[] colsAlign, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStringArray(colsTextArr);
          _data.writeIntArray(colsWidthArr);
          _data.writeIntArray(colsAlign);
          _data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_printColumnsText, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().printColumnsText(colsTextArr, colsWidthArr, colsAlign, callback);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
          * print pictures
          * bitmap: the maximum width is 384 pixels, if the width is exceeded, the display will be incomplete; the image size length * width <8M;
          */
      @Override public void printBitmap(android.graphics.Bitmap bitmap, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          if ((bitmap!=null)) {
            _data.writeInt(1);
            bitmap.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          _data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_printBitmap, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().printBitmap(bitmap, callback);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
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
      @Override public void printBarCode(java.lang.String data, int symbology, int height, int width, int textposition, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(data);
          _data.writeInt(symbology);
          _data.writeInt(height);
          _data.writeInt(width);
          _data.writeInt(textposition);
          _data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_printBarCode, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().printBarCode(data, symbology, height, width, textposition, callback);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
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
      @Override public void printQRCode(java.lang.String data, int modulesize, int errorlevel, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(data);
          _data.writeInt(modulesize);
          _data.writeInt(errorlevel);
          _data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_printQRCode, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().printQRCode(data, modulesize, errorlevel, callback);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
          * Print text, the text width will automatically wrap when one line is full, and if it is less than one line, it will not be printed unless forced to wrap
          * The text is output as the width of the vector text, that is, each character is not of equal width
          * text: the text string to print
          */
      @Override public void printOriginalText(java.lang.String text, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(text);
          _data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_printOriginalText, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().printOriginalText(text, callback);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
          * lib package printing dedicated interface
          * transbean: print task list
          */
      @Override public void commitPrint(com.sunmi.trans.TransBean[] transbean, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedArray(transbean, 0);
          _data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_commitPrint, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().commitPrint(transbean, callback);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
          * print buffer content
          */
      @Override public void commitPrinterBuffer() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_commitPrinterBuffer, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().commitPrinterBuffer();
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
          * Enter transaction mode, all print calls will be cached;
          * Call commitPrinterBuffer(), exitPrinterBuffer(true), commitPrinterBufferWithCallback(),
          * print after exitPrinterBufferWithCallback(true);
          * clean: whether to clear the cached buffer content if the transaction mode has not been exited before
          */
      @Override public void enterPrinterBuffer(boolean clean) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(((clean)?(1):(0)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_enterPrinterBuffer, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().enterPrinterBuffer(clean);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
          * Exit buffer mode
          * commit: whether to print out the buffer content
          */
      @Override public void exitPrinterBuffer(boolean commit) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(((commit)?(1):(0)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_exitPrinterBuffer, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().exitPrinterBuffer(commit);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
          * Send NC commands
          * data: tax control order
          */
      @Override public void tax(byte[] data, woyou.aidlservice.jiuiv5.ITax callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeByteArray(data);
          _data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_tax, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().tax(data, callback);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
          * Get the model of the printer head
          * Returned in callback onReturnString
          */
      @Override public void getPrinterFactory(woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_getPrinterFactory, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().getPrinterFactory(callback);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
          * Clear printer cache data (only system calls, external calls are invalid)
          */
      @Override public void clearBuffer() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_clearBuffer, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().clearBuffer();
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
          * Print buffer content with feedback
          */
      @Override public void commitPrinterBufferWithCallback(woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_commitPrinterBufferWithCallback, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().commitPrinterBufferWithCallback(callback);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
          * Exit buffer print mode with feedback
          * commit: whether to commit the buffer content
          */
      @Override public void exitPrinterBufferWithCallback(boolean commit, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(((commit)?(1):(0)));
          _data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_exitPrinterBufferWithCallback, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().exitPrinterBufferWithCallback(commit, callback);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
          * Print a row of the table, you can specify the column width and alignment
          * colsTextArr: Array of text strings for each column
          * colsWidthArr: The width weight of each column is the proportion of each column
          * colsAlign: Alignment of each column (0 to the left, 1 to the center, 2 to the right)
          * Remark: The array length of the three parameters should be the same, if the width of colsText[i] is greater than colsWidth[i], the text wraps
          */
      @Override public void printColumnsString(java.lang.String[] colsTextArr, int[] colsWidthArr, int[] colsAlign, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStringArray(colsTextArr);
          _data.writeIntArray(colsWidthArr);
          _data.writeIntArray(colsAlign);
          _data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_printColumnsString, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().printColumnsString(colsTextArr, colsWidthArr, colsAlign, callback);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
          * Get the latest status of the printer
          * Return: printer status feedback 1 normal 2 in preparation 3 communication abnormal 4 out of paper 5 overheated 505: no printer 507: update failed
          */
      @Override public int updatePrinterState() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_updatePrinterState, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().updatePrinterState();
          }
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
          * Custom print pictures
          * bitmap: image bitmap object (maximum width is 384 pixels, if the image exceeds 1M, it cannot be printed)
          * type: There are currently two printing methods: 0, same as printBitmap 1, black and white image with a threshold of 200 2, grayscale image
          * Supported version: P1-v3.2.0 and above
          * P14g-v1.1.6 and above
          * V1s-v3.1.6 and above
          * Above V2-v1.0.0
          */
      @Override public void printBitmapCustom(android.graphics.Bitmap bitmap, int type, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          if ((bitmap!=null)) {
            _data.writeInt(1);
            bitmap.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          _data.writeInt(type);
          _data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_printBitmapCustom, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().printBitmapCustom(bitmap, type, callback);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
          * Get the force-enabled font doubling state
          * return 0: not enabled 1: double width 2: double height 3: double height and double width
          * Supported version: P1-v3.2.0 and above
          * P14g-v1.2.0 and above
          * Above V1s-v3.2.0
          * Above V2-v1.0.0
          */
      @Override public int getForcedDouble() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getForcedDouble, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getForcedDouble();
          }
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
          * Whether to force the anti-white style to be enabled
          * return true: enabled false: not enabled
          * Supported version: P1-v3.2.0 and above
          * P14g-v1.2.0 and above
          * Above V1s-v3.2.0
          * Above V2-v1.0.0
          */
      @Override public boolean isForcedAntiWhite() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_isForcedAntiWhite, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().isForcedAntiWhite();
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
          * Whether to force the bold style to be enabled
          * return true: enabled false: not enabled
          * Supported version: P1-v3.2.0 and above
          * P14g-v1.2.0 and above
          * Above V1s-v3.2.0
          * Above V2-v1.0.0
          */
      @Override public boolean isForcedBold() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_isForcedBold, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().isForcedBold();
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
          * Whether to force the underline style to be enabled
          * return true: enabled false: not enabled
          * Supported version: P1-v3.2.0 and above
          * P14g-v1.2.0 and above
          * Above V1s-v3.2.0
          * Above V2-v1.0.0
          */
      @Override public boolean isForcedUnderline() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_isForcedUnderline, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().isForcedUnderline();
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
          * Get force-enable row-height status
          * return -1: not enabled 0~255: force line height pixel height
          * Supported version: P1-v3.2.0 and above
          * P14g-v1.2.0 and above
          * Above V1s-v3.2.0
          * Above V2-v1.0.0
          */
      @Override public int getForcedRowHeight() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getForcedRowHeight, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getForcedRowHeight();
          }
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
          * Get the current font
          * Return 0: Sunmi Font 1.0 1: Sunmi Font 2.0
          * Supported version: P1-v3.2.0 and above
          * P14g-v1.2.0 and above
          * Above V1s-v3.2.0
          * Above V2-v1.0.0
          */
      @Override public int getFontName() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getFontName, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getFontName();
          }
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      public static woyou.aidlservice.jiuiv5.IWoyouService sDefaultImpl;
    }
    static final int TRANSACTION_postPrintData = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_getFirmwareStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    static final int TRANSACTION_getServiceVersion = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
    static final int TRANSACTION_printerInit = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
    static final int TRANSACTION_printerSelfChecking = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
    static final int TRANSACTION_getPrinterSerialNo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
    static final int TRANSACTION_getPrinterVersion = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
    static final int TRANSACTION_getPrinterModal = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
    static final int TRANSACTION_getPrintedLength = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
    static final int TRANSACTION_lineWrap = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
    static final int TRANSACTION_sendRAWData = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
    static final int TRANSACTION_setAlignment = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
    static final int TRANSACTION_setFontName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
    static final int TRANSACTION_setFontSize = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
    static final int TRANSACTION_printText = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
    static final int TRANSACTION_printTextWithFont = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
    static final int TRANSACTION_printColumnsText = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
    static final int TRANSACTION_printBitmap = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
    static final int TRANSACTION_printBarCode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
    static final int TRANSACTION_printQRCode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
    static final int TRANSACTION_printOriginalText = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
    static final int TRANSACTION_commitPrint = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
    static final int TRANSACTION_commitPrinterBuffer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
    static final int TRANSACTION_enterPrinterBuffer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
    static final int TRANSACTION_exitPrinterBuffer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
    static final int TRANSACTION_tax = (android.os.IBinder.FIRST_CALL_TRANSACTION + 25);
    static final int TRANSACTION_getPrinterFactory = (android.os.IBinder.FIRST_CALL_TRANSACTION + 26);
    static final int TRANSACTION_clearBuffer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 27);
    static final int TRANSACTION_commitPrinterBufferWithCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 28);
    static final int TRANSACTION_exitPrinterBufferWithCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 29);
    static final int TRANSACTION_printColumnsString = (android.os.IBinder.FIRST_CALL_TRANSACTION + 30);
    static final int TRANSACTION_updatePrinterState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 31);
    static final int TRANSACTION_printBitmapCustom = (android.os.IBinder.FIRST_CALL_TRANSACTION + 32);
    static final int TRANSACTION_getForcedDouble = (android.os.IBinder.FIRST_CALL_TRANSACTION + 33);
    static final int TRANSACTION_isForcedAntiWhite = (android.os.IBinder.FIRST_CALL_TRANSACTION + 34);
    static final int TRANSACTION_isForcedBold = (android.os.IBinder.FIRST_CALL_TRANSACTION + 35);
    static final int TRANSACTION_isForcedUnderline = (android.os.IBinder.FIRST_CALL_TRANSACTION + 36);
    static final int TRANSACTION_getForcedRowHeight = (android.os.IBinder.FIRST_CALL_TRANSACTION + 37);
    static final int TRANSACTION_getFontName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 38);
    public static boolean setDefaultImpl(woyou.aidlservice.jiuiv5.IWoyouService impl) {
      // Only one user of this interface can use this function
      // at a time. This is a heuristic to detect if two different
      // users in the same process use this function.
      if (Stub.Proxy.sDefaultImpl != null) {
        throw new IllegalStateException("setDefaultImpl() called twice");
      }
      if (impl != null) {
        Stub.Proxy.sDefaultImpl = impl;
        return true;
      }
      return false;
    }
    public static woyou.aidlservice.jiuiv5.IWoyouService getDefaultImpl() {
      return Stub.Proxy.sDefaultImpl;
    }
  }
  /**
      * Replace the original printer upgrade firmware interface (void updateFirmware())
      * Now changed to the data interface of the payload package name, only system calls
      * Supported version: above 4.0.0
      */
  public boolean postPrintData(java.lang.String packageName, byte[] data, int offset, int length) throws android.os.RemoteException;
  /**
      * Printer firmware status
      * Return: 0--unknown, A5--bootloader, C3--print
      */
  public int getFirmwareStatus() throws android.os.RemoteException;
  /**
      * Get print service version
      * Return: WoyouService service version
      */
  public java.lang.String getServiceVersion() throws android.os.RemoteException;
  /**
      * Initialize the printer, reset the logic program of the printer, but do not clear the buffer data, so
      * Incomplete print jobs will continue after reset
      */
  public void printerInit(woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException;
  /**
      * Printer self-test, the printer will print a self-test page
      */
  public void printerSelfChecking(woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException;
  /**
      * Get the serial number of the printer board
      * Returns: Serial number of the printer board
      */
  public java.lang.String getPrinterSerialNo() throws android.os.RemoteException;
  /**
      * Get the printer firmware version number
      * Return: Printer firmware version number
      */
  public java.lang.String getPrinterVersion() throws android.os.RemoteException;
  /**
      * Get the printer model
      * Return: Printer Model
      */
  public java.lang.String getPrinterModal() throws android.os.RemoteException;
  /**
      * Get the print length after the printer is powered on
      * Returned in callback onReturnString
      */
  public void getPrintedLength(woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException;
  /**
      * The printer feeds the paper (forced line feed, and feeds n lines after finishing the previous print content)
      * n: the number of paper lines
      */
  public void lineWrap(int n, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException;
  /**
      * epson command print
      */
  public void sendRAWData(byte[] data, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException;
  /**
      * Set the alignment mode, which will affect subsequent printing unless initialized
      * alignment: alignment 0--left, 1--center, 2--right
      */
  public void setAlignment(int alignment, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException;
  /**
      * Set the print font, temporarily only system calls are available, external calls are invalid
      */
  public void setFontName(java.lang.String typeface, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException;
  /**
      * Set the font size, which will affect subsequent printing unless initialized
      *Note: Font size is beyond standard international instructions for printing,
      * Adjusting the font size will affect the character width, and the number of characters per line will also change,
      * Therefore, typography in monospaced fonts may be messed up
      * fontsize: font size
      */
  public void setFontSize(float fontsize, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException;
  /**
      * Print text, the text width will automatically wrap when one line is full, and if it is less than one line, it will not be printed unless forced to wrap
      * text: the text string to print
      */
  public void printText(java.lang.String text, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException;
  /**
      * Print the text of the specified font, the font setting is only valid for this time
      * text: the text to print
      * typeface: font name (temporarily only system calls, external calls are invalid)
      * fontsize: font size
      */
  public void printTextWithFont(java.lang.String text, java.lang.String typeface, float fontsize, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException;
  /**
      * Print a row of the table, you can specify the column width and alignment
      * colsTextArr: Array of text strings for each column
      * colsWidthArr: Array of the width of each column (calculated in English characters, each Chinese character occupies two English characters, each width is greater than 0)
      * colsAlign: Alignment of each column (0 to the left, 1 to the center, 2 to the right)
      * Remark: The array length of the three parameters should be the same, if the width of colsText[i] is greater than colsWidth[i], the text wraps
      */
  public void printColumnsText(java.lang.String[] colsTextArr, int[] colsWidthArr, int[] colsAlign, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException;
  /**
      * print pictures
      * bitmap: the maximum width is 384 pixels, if the width is exceeded, the display will be incomplete; the image size length * width <8M;
      */
  public void printBitmap(android.graphics.Bitmap bitmap, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException;
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
  public void printBarCode(java.lang.String data, int symbology, int height, int width, int textposition, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException;
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
  public void printQRCode(java.lang.String data, int modulesize, int errorlevel, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException;
  /**
      * Print text, the text width will automatically wrap when one line is full, and if it is less than one line, it will not be printed unless forced to wrap
      * The text is output as the width of the vector text, that is, each character is not of equal width
      * text: the text string to print
      */
  public void printOriginalText(java.lang.String text, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException;
  /**
      * lib package printing dedicated interface
      * transbean: print task list
      */
  public void commitPrint(com.sunmi.trans.TransBean[] transbean, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException;
  /**
      * print buffer content
      */
  public void commitPrinterBuffer() throws android.os.RemoteException;
  /**
      * Enter transaction mode, all print calls will be cached;
      * Call commitPrinterBuffer(), exitPrinterBuffer(true), commitPrinterBufferWithCallback(),
      * print after exitPrinterBufferWithCallback(true);
      * clean: whether to clear the cached buffer content if the transaction mode has not been exited before
      */
  public void enterPrinterBuffer(boolean clean) throws android.os.RemoteException;
  /**
      * Exit buffer mode
      * commit: whether to print out the buffer content
      */
  public void exitPrinterBuffer(boolean commit) throws android.os.RemoteException;
  /**
      * Send NC commands
      * data: tax control order
      */
  public void tax(byte[] data, woyou.aidlservice.jiuiv5.ITax callback) throws android.os.RemoteException;
  /**
      * Get the model of the printer head
      * Returned in callback onReturnString
      */
  public void getPrinterFactory(woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException;
  /**
      * Clear printer cache data (only system calls, external calls are invalid)
      */
  public void clearBuffer() throws android.os.RemoteException;
  /**
      * Print buffer content with feedback
      */
  public void commitPrinterBufferWithCallback(woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException;
  /**
      * Exit buffer print mode with feedback
      * commit: whether to commit the buffer content
      */
  public void exitPrinterBufferWithCallback(boolean commit, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException;
  /**
      * Print a row of the table, you can specify the column width and alignment
      * colsTextArr: Array of text strings for each column
      * colsWidthArr: The width weight of each column is the proportion of each column
      * colsAlign: Alignment of each column (0 to the left, 1 to the center, 2 to the right)
      * Remark: The array length of the three parameters should be the same, if the width of colsText[i] is greater than colsWidth[i], the text wraps
      */
  public void printColumnsString(java.lang.String[] colsTextArr, int[] colsWidthArr, int[] colsAlign, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException;
  /**
      * Get the latest status of the printer
      * Return: printer status feedback 1 normal 2 in preparation 3 communication abnormal 4 out of paper 5 overheated 505: no printer 507: update failed
      */
  public int updatePrinterState() throws android.os.RemoteException;
  /**
      * Custom print pictures
      * bitmap: image bitmap object (maximum width is 384 pixels, if the image exceeds 1M, it cannot be printed)
      * type: There are currently two printing methods: 0, same as printBitmap 1, black and white image with a threshold of 200 2, grayscale image
      * Supported version: P1-v3.2.0 and above
      * P14g-v1.1.6 and above
      * V1s-v3.1.6 and above
      * Above V2-v1.0.0
      */
  public void printBitmapCustom(android.graphics.Bitmap bitmap, int type, woyou.aidlservice.jiuiv5.ICallback callback) throws android.os.RemoteException;
  /**
      * Get the force-enabled font doubling state
      * return 0: not enabled 1: double width 2: double height 3: double height and double width
      * Supported version: P1-v3.2.0 and above
      * P14g-v1.2.0 and above
      * Above V1s-v3.2.0
      * Above V2-v1.0.0
      */
  public int getForcedDouble() throws android.os.RemoteException;
  /**
      * Whether to force the anti-white style to be enabled
      * return true: enabled false: not enabled
      * Supported version: P1-v3.2.0 and above
      * P14g-v1.2.0 and above
      * Above V1s-v3.2.0
      * Above V2-v1.0.0
      */
  public boolean isForcedAntiWhite() throws android.os.RemoteException;
  /**
      * Whether to force the bold style to be enabled
      * return true: enabled false: not enabled
      * Supported version: P1-v3.2.0 and above
      * P14g-v1.2.0 and above
      * Above V1s-v3.2.0
      * Above V2-v1.0.0
      */
  public boolean isForcedBold() throws android.os.RemoteException;
  /**
      * Whether to force the underline style to be enabled
      * return true: enabled false: not enabled
      * Supported version: P1-v3.2.0 and above
      * P14g-v1.2.0 and above
      * Above V1s-v3.2.0
      * Above V2-v1.0.0
      */
  public boolean isForcedUnderline() throws android.os.RemoteException;
  /**
      * Get force-enable row-height status
      * return -1: not enabled 0~255: force line height pixel height
      * Supported version: P1-v3.2.0 and above
      * P14g-v1.2.0 and above
      * Above V1s-v3.2.0
      * Above V2-v1.0.0
      */
  public int getForcedRowHeight() throws android.os.RemoteException;
  /**
      * Get the current font
      * Return 0: Sunmi Font 1.0 1: Sunmi Font 2.0
      * Supported version: P1-v3.2.0 and above
      * P14g-v1.2.0 and above
      * Above V1s-v3.2.0
      * Above V2-v1.0.0
      */
  public int getFontName() throws android.os.RemoteException;
}
