WINE=wine
PREFIX=wine-prefix

JAVA_URL="https://download.java.net/java/GA/jdk19.0.2/fdb695a9d9064ad6b064dc6df578380c/7/GPL/openjdk-19.0.2_windows-x64_bin.zip"
NSIS_URL="https://netix.dl.sourceforge.net/project/nsis/NSIS%203/3.08/nsis-3.08.zip"

REALPREFIX=$(readlink -f $PREFIX)
C_DRIVE=$REALPREFIX/drive_c

if [ "$1" == "prepare" ]; then
    echo Setuping Wine...
    WINEPREFIX=$REALPREFIX $WINE wineboot

    echo Downloading OpenJDK...
    wget $JAVA_URL -O $C_DRIVE/java.zip -q --show-progress
    echo Downloading NSIS...
    wget $NSIS_URL -O $C_DRIVE/nsis.zip -q --show-progress

    echo Unzipping OpenJDK...
    unzip $C_DRIVE/java.zip -d $C_DRIVE/java
    echo Unzipping NSIS...
    unzip $C_DRIVE/nsis.zip -d $C_DRIVE/nsis
else
    JDK_DIR=$(ls $REALPREFIX/drive_c/java | head -c -1)
    JAVA_DIR_WINE="C:\\java\\$JDK_DIR"

    NSIS_DIR=$(ls $REALPREFIX/drive_c/nsis | head -c -1)
    NSIS_DIR_WINE="C:\\nsis\\$NSIS_DIR"

    WINEPREFIX=$REALPREFIX $WINE cmd.exe /k "set JAVA_HOME=$JAVA_DIR_WINE&& set PATH=%PATH%;$JAVA_DIR_WINE\\bin;$NSIS_DIR_WINE"
fi

