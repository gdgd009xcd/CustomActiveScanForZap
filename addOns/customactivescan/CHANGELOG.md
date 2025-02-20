# Changelog
All notable changes to this add-on will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).

## [v0.8.18] - 2025-01-22
### Changed
- bugfix: Fixed a weird popup menu that enlarge each time when it is shown in the ScanLog panel.

## [v0.8.17] - 2025-01-20
### Changed
- bugfix: fixed null exception errors with using CustomScan Main Panel GUI. I apologize for the inconvenience.

## [v0.8.16] - 2024-12-21-02
### Changed
- maintenance: Tweaked some description in README.md for generating page with jeykill 

## [v0.8.16] - 2024-12-21
### Changed
- maintenance: Tweaked some description in README.md

## [v0.8.16] - 2024-12-20-02
### Changed
- maintenance: Tweaked some description in README.md

## [v0.8.16] - 2024-12-20
### Changed
- maintenance: Added some description to README.md

## [v0.8.16] - 2024-12-20
### Changed
- maintenance: Tweaked codes to prepare for new java version and ubuntu 24.04

## [v0.8.15] - 2024-12-17
### Changed
- maintenance: Code was changed to prepare for new java version and ubuntu 24.04
- maintenance: Added description to README.md for compiling with IntelliJ IDEA.

## [v0.8.14] - 2024-12-07
### Changed
- maintenance: added some information to README.md

## [v0.8.13] - 2024-12-05
### Changed
- maintenance: changed "Custom Vector" encoding behavior for supporting "Decode Parameter" feature in RequestRecorder

## [v0.8.12] - 2024-06-20
### Changed
- bugfix: fixed problem of RegexTextDialog which lost displaying component such as CR icon after editing contents.

## [v0.8.11] - 2024-06-01
### Changed
- maintenance: added javahelp files for supporting locales.

## [v0.8.10] - 2024-05-31
### Changed
- bugfix: fixed problemn which can't display this addon's javahelp when user select language except English.

## [v0.8.9] - 2024-03-28
### Changed
- bugfix: Changed to correctly encode and decode the HttpRequest body based on Content-Encoding.

## [v0.8.8] - 2024-03-12
### Added
- new feature: Supported URLEncoded(%XX) value within pattern for embeding binary data on the request. see [this](https://github.com/gdgd009xcd/CustomActiveScanForZAP/wiki/2.0.-CustomActiveScan-Main-Panel/#5-decode-urlencodedxx-value-check-box)

## [v0.8.7] - 2024-02-21
### Changed
- bugfix: fixed an issue where deleting a row in the ScanRule table would cause an exception if the ComboBox had input focus.
- maintenance: Changed some hardcoded text to I18N text.
### Added
- improve: Added javahelp button in MenuBar of main panel.


## [v0.8.6] - 2024-01-12
### Added
- improve: Supported custom vectors which specify custom locations in the http request on active scanning.

## [v0.8.5] - 2023-12-14
### Added
- new feature: Added next▼/prev▲　button action which move caret to next/prev Lcs position on Result Dialog.
### Changed
- maintenance: change gradle-build-action version from v2 to v2.11.0

## [v0.8.4] - 2023-12-02
### Changed
- improve: improved displaying performance of the Result Dialog.

## [v0.8.3] - 2023-11-23
### Added
- maintenance: add "Default Sample SQL/Pentest pattern" to "CopyRule->Select Scan Rule Copy From" menu
### Changed
- maintenance: updated README.md, wiki pages.

## [v0.8.2] - 2023-11-22
### Added
- improve: added Parameter Name column to ScanLogPanel dialog.
### Changed
- bugfix : fixed PopUp menu is appeared in outsite of the ScanLogPanel.
- maintenance: updated SQL injection test results.

## [v0.8.1] - 2023-11-20
### Changed
- improve : Added new column in [main panel](https://github.com/gdgd009xcd/CustomActiveScanForZAP/wiki/2.0.-CustomActiveScan-Main-Panel#1-sql-rule-type)
- improve : [the result dialog](https://github.com/gdgd009xcd/CustomActiveScanForZAP/wiki/2.0.-CustomActiveScan-Main-Panel#4-result-dialog) always shows LCS compare results whether scanner detects injection or not.
- maintenance: updated readme.md, wiki page

## [v0.8.0] - 2023-11-13
### Changed
- improve: display detected SQL injection on ScanLogPanel. now default befaviour while scanning is opened ScanLogPanel. but you can change this behaviour using [this checkbox](https://github.com/gdgd009xcd/CustomActiveScanForZAP/wiki/2.0.-CustomActiveScan-Tab-Panel#scanlog-panel)
- improve: detected alert information is displayed at ScanLogPanel->popup the Result dialog. the Result Dialog shows compare LCS result of request/response and alert information.
- bugfix: In Regex Test Dialog, Fixed incorrect behavior of the "Next" button that has skipped the next search string
- improve: added new buttons/checkboxes to Regex Test Dialog. ([▲]Prev/[▼]Next Search button etc...) 
- bugfix: Fixed a bug in the scanner pause function. If you close the ScanLog panel, the scan will be infinite waiting state in some situation.
- maintenance: Added a function to cooperate with automacro builder.
- maintenance: update README.md for adding image.

## 22 - 2023-10-18
### v0.7.6
- maintenance: applied ”spotlessApply" to .kts files 
- maintenance: applied JTextPane to TextPaneWrap classes for wrapping long line text. 
- new feature: added [Github pages](https://gdgd009xcd.github.io/CustomActiveScanForZAP) 

## 21 - 2023-10-11
### v0.7.5
- improve: added load/save file menu
- improve: refined scanlog window's table selection mechanism
- bugfix: fixed regex search modal dialog problem(popup dialog hide below parent window)

## 20 - 2023-10-08
### v0.7.4
- bugfix: fixed falsepositive SQL injection detect. (3-2. true response contains part of the original response)
- upgrade: dependent "org.zaproxy.add-on" version upgraded to 0.8.0
- upgrade: gradle version upgraded to 8.2.1
- upgrade: dependent zap version upgraded to 2.13.0
- maintenance: Due to side effects of the above upgrade, some code has been changed or removed.

## 19 - 2023-09-20
### v0.7.3
- improve: refine CustomActiveScan logic(3-2. true response contains part of the original response)

## 18 - 2023-09-17
### v0.7.2
- improve: refine CustomActiveScan logic(3-2. true response contains part of the original response)

## 17 - 2023-09-13
### v0.7.1
- improve: refine CustomActiveScan logic(3-2. true response contains part of the original response)

## 16 - 2023-08-30
### v0.7.0
- bugfix: fix problems about "scanLog window"
- improve: refine CustomActiveScan logic

## 15 - 2023-06-17
### v0.6.4
- maintenance: message support for I18N

## 14 - 2023-06-14
### v0.6.3
- new feature: "PenTest" customizable active scan feature.

## 13 - 2022-06-20
### v0.5.7
- maintenance: setting limit on debug print string value and convert control codes to printable string(represented as a URLencoded string) 
- maintenance: updated gson library to the latest version

## 12 - 2021-12-15
### v0.5.6
- maintenance: removed the log4j2 library that this add-on contains
- maintenance: upgraded ZAP dependency version  to 2.11.0

## 11 - 2021-12-11
### v0.5.5
- bugfix: There were false positive in the judgement code in 3-1,3-2
- maintenance: Updated log4j to the latest version

## 10 - 2021-12-02
### v0.5.4
- improve: Stop [Stop Active Scan] button is now supported
- maintenance: This repository is now checked at lgtm.com

## 9 - 2021-12-01
### v0.5.3
- maintenance: removed some redundant codes

## 8 - 2021-11-21
### v0.5.2
- bugfix: There was an false negative in the judgment code in 3-1
- improve: removed redundant code.(2-1, 2-2) because these codes are the same as 3-1, 3-2.
- maintenance: added new debuglevel DEBUGBINGO to LOG4J 

## 7 - 2021-09-26
### v0.5.1
- bugfix: there are some false positive at scan rule judgement

## 6 - 2021-09-18
### v0.5.0
- improve: Enhanced detection of small changes in page content
- new feature: detection of mongo db sql injection
## 5 - 2021-06-01
- improve: LOG4J prints stacktrace when sendAndReceive raises an exception
- bugfix: Set Multi-Release: true in the MANIFEST.MF file to use Java 9+
## 4 - 2020-11-16
### v0.0.5
- Header content is also included in the response when injection is detected.
- if response sizeody) < CustomSQLInjectionScanRule.MAXMASKBODYSIZE then, random id values convert to asterisk in response. This makes improve to detect small difference in response
## 3 - 2020-09-21
### v0.0.4
- changed addOnName, manifest.repo in .gradle.kts.
- help file added.
## 2 - 2020-09-17
### v0.0.3
- getId changed to 40037
## 1 - 2020-09-02
- first landing on github.

## [v0.0.0] - 1970-0101
### Added
- xxxx...xx
### Changed
### Deprecated
### Removed
### Fixed
### Security
### Sorry..




