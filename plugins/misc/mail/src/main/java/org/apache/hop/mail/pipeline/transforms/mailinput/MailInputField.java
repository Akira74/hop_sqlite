/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hop.mail.pipeline.transforms.mailinput;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.apache.hop.i18n.BaseMessages;
import org.apache.hop.metadata.api.HopMetadataProperty;

/** Describes an Mail input field */
@Getter
@Setter
public class MailInputField implements Cloneable {
  private static final Class<?> PKG = MailInputMeta.class;

  public static final int COLUMN_MESSAGE_NR = 0;
  public static final int COLUMN_SUBJECT = 1;
  public static final int COLUMN_SENDER = 2;
  public static final int COLUMN_REPLY_TO = 3;
  public static final int COLUMN_RECIPIENTS = 4;
  public static final int COLUMN_DESCRIPTION = 5;
  public static final int COLUMN_BODY = 6;
  public static final int COLUMN_RECEIVED_DATE = 7;
  public static final int COLUMN_SENT_DATE = 8;
  public static final int COLUMN_CONTENT_TYPE = 9;
  public static final int COLUMN_FOLDER_NAME = 10;
  public static final int COLUMN_SIZE = 11;
  public static final int COLUMN_FLAG_NEW = 12;
  public static final int COLUMN_FLAG_READ = 13;
  public static final int COLUMN_FLAG_FLAGGED = 14;
  public static final int COLUMN_FLAG_DRAFT = 15;
  public static final int COLUMN_FLAG_DELETED = 16;
  public static final int COLUMN_ATTACHED_FILES_COUNT = 17;
  public static final int COLUMN_HEADER = 18;
  public static final int COLUMN_BODY_CONTENT_TYPE = 19;

  public static final String[] ColumnCode = {
    "messagenumber", "subject", "sender", "replyto", "recipients", "description", "body",
        "receiveddate",
    "sendeddate", "contenttype", "folder", "size", "flag_new", "flag_read", "flag_flagged",
        "flag_draft",
    "flag_deleted", "attached_files_count", "header", "body_contenttype"
  };

  public static final String[] ColumnDesc = {
    BaseMessages.getString(PKG, "MailInputField.Column.MessageNumber"),
    BaseMessages.getString(PKG, "MailInputField.Column.Subject"),
    BaseMessages.getString(PKG, "MailInputField.Column.Sender"),
    BaseMessages.getString(PKG, "MailInputField.Column.ReplyTo"),
    BaseMessages.getString(PKG, "MailInputField.Column.Recipients"),
    BaseMessages.getString(PKG, "MailInputField.Column.Description"),
    BaseMessages.getString(PKG, "MailInputField.Column.Body"),
    BaseMessages.getString(PKG, "MailInputField.Column.ReceivedDate"),
    BaseMessages.getString(PKG, "MailInputField.Column.SentDate"),
    BaseMessages.getString(PKG, "MailInputField.Column.ContentType"),
    BaseMessages.getString(PKG, "MailInputField.Column.Folder"),
    BaseMessages.getString(PKG, "MailInputField.Column.Size"),
    BaseMessages.getString(PKG, "MailInputField.Column.FlagNew"),
    BaseMessages.getString(PKG, "MailInputField.Column.FlagRead"),
    BaseMessages.getString(PKG, "MailInputField.Column.FlagFlagged"),
    BaseMessages.getString(PKG, "MailInputField.Column.FlagDraft"),
    BaseMessages.getString(PKG, "MailInputField.Column.FlagDeleted"),
    BaseMessages.getString(PKG, "MailInputField.Column.AttachedFilesCount"),
    BaseMessages.getString(PKG, "MailInputField.Column.Header"),
    BaseMessages.getString(PKG, "MailInputField.Column.BodyContentType"),
  };

  public static final Map<String, String> columns =
      new HashMap<String, String>() {
        {
          put("messagenumber", BaseMessages.getString(PKG, "MailInputField.Column.MessageNumber"));
          put("subject", BaseMessages.getString(PKG, "MailInputField.Column.Subject"));
          put("sender", BaseMessages.getString(PKG, "MailInputField.Column.Sender"));
          put("replyto", BaseMessages.getString(PKG, "MailInputField.Column.ReplyTo"));
          put("recipients", BaseMessages.getString(PKG, "MailInputField.Column.Recipients"));
          put("description", BaseMessages.getString(PKG, "MailInputField.Column.Description"));
          put("body", BaseMessages.getString(PKG, "MailInputField.Column.Body"));
          put("receiveddate", BaseMessages.getString(PKG, "MailInputField.Column.ReceivedDate"));
          put("sentdate", BaseMessages.getString(PKG, "MailInputField.Column.SentDate"));
          put("contenttype", BaseMessages.getString(PKG, "MailInputField.Column.ContentType"));
          put("folder", BaseMessages.getString(PKG, "MailInputField.Column.Folder"));
          put("size", BaseMessages.getString(PKG, "MailInputField.Column.Size"));
          put("flag_new", BaseMessages.getString(PKG, "MailInputField.Column.FlagNew"));
          put("flag_read", BaseMessages.getString(PKG, "MailInputField.Column.FlagRead"));
          put("flag_flagged", BaseMessages.getString(PKG, "MailInputField.Column.FlagFlagged"));
          put("flag_draft", BaseMessages.getString(PKG, "MailInputField.Column.FlagDraft"));
          put("flag_deleted", BaseMessages.getString(PKG, "MailInputField.Column.FlagDeleted"));
          put(
              "attached_files_count",
              BaseMessages.getString(PKG, "MailInputField.Column.AttachedFilesCount"));
          put("header", BaseMessages.getString(PKG, "MailInputField.Column.Header"));
          put(
              "body_contenttype",
              BaseMessages.getString(PKG, "MailInputField.Column.BodyContentType"));
        }
      };
  List<String> columnDescs = Arrays.asList(ColumnDesc);

  @HopMetadataProperty private String name;

  @HopMetadataProperty private String column;

  public MailInputField(String fieldname) {
    this.name = fieldname;
    //    this.column = COLUMN_MESSAGE_NR;
  }

  public MailInputField() {
    //    this(null);
  }

  public String getColumnDesc() {
    return getColumnDesc(columnDescs.indexOf(column));
  }

  public static final String getColumnDesc(int i) {
    if (i < 0 || i >= ColumnDesc.length) {
      return ColumnDesc[0];
    }
    return ColumnDesc[i];
  }

  public int getColumn() {
    //    return column;
    return getColumnByDesc(name);
  }

  public String getColumnCode() {

    return getColumnCode(columnDescs.indexOf(column));
  }

  public static final String getColumnCode(int i) {
    if (i < 0 || i >= ColumnCode.length) {
      return ColumnCode[0];
    }
    return ColumnCode[i];
  }

  public static final int getColumnByCode(String tt) {
    if (tt == null) {
      return 0;
    }

    for (int i = 0; i < ColumnCode.length; i++) {
      if (ColumnCode[i].equalsIgnoreCase(tt)) {
        return i;
      }
    }
    return 0;
  }

  @Override
  public Object clone() {
    try {
      MailInputField retval = (MailInputField) super.clone();
      return retval;
    } catch (CloneNotSupportedException e) {
      return null;
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String fieldname) {
    this.name = fieldname;
  }

  public static final int getColumnByDesc(String tt) {
    if (tt == null) {
      return 0;
    }

    for (int i = 0; i < ColumnDesc.length; i++) {
      if (ColumnDesc[i].equalsIgnoreCase(tt)) {
        return i;
      }
    }
    return 0;
  }

  public void setColumn(String fieldname) {
    this.column = fieldname;
  }

  public void setColumn(int column) {
    this.column = ColumnDesc[column];
  }
}
