/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hop.mail.workflow.actions.mailvalidator;

import org.apache.hop.core.Const;
import org.apache.hop.core.util.Utils;
import org.apache.hop.core.variables.IVariables;
import org.apache.hop.i18n.BaseMessages;
import org.apache.hop.ui.core.PropsUi;
import org.apache.hop.ui.core.dialog.BaseDialog;
import org.apache.hop.ui.core.gui.WindowProperty;
import org.apache.hop.ui.core.widget.LabelTextVar;
import org.apache.hop.ui.core.widget.TextVar;
import org.apache.hop.ui.pipeline.transform.BaseTransformDialog;
import org.apache.hop.ui.workflow.action.ActionDialog;
import org.apache.hop.ui.workflow.dialog.WorkflowDialog;
import org.apache.hop.workflow.WorkflowMeta;
import org.apache.hop.workflow.action.IAction;
import org.apache.hop.workflow.action.IActionDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ActionMailValidatorDialog extends ActionDialog implements IActionDialog {
  private static final Class<?> PKG = ActionMailValidator.class;

  private Text wName;

  private ActionMailValidator action;

  private Shell shell;

  private boolean changed;

  private LabelTextVar wMailAddress;

  private Label wleMailSender;
  private TextVar weMailSender;

  private Label wlTimeOut;
  private TextVar wTimeOut;

  private Label wlDefaultSMTP;
  private TextVar wDefaultSMTP;

  private Button wSMTPCheck;

  public ActionMailValidatorDialog(
      Shell parent, IAction action, WorkflowMeta workflowMeta, IVariables variables) {
    super(parent, workflowMeta, variables);
    this.action = (ActionMailValidator) action;

    if (this.action.getName() == null) {
      this.action.setName(BaseMessages.getString(PKG, "ActionMailValidatorDialog.Name.Default"));
    }
  }

  @Override
  public IAction open() {
    Shell parent = getParent();

    shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.MIN | SWT.MAX | SWT.RESIZE);
    PropsUi.setLook(shell);
    WorkflowDialog.setShellImage(shell, action);

    ModifyListener lsMod = e -> action.setChanged();
    changed = action.hasChanged();

    FormLayout formLayout = new FormLayout();
    formLayout.marginWidth = PropsUi.getFormMargin();
    formLayout.marginHeight = PropsUi.getFormMargin();

    shell.setLayout(formLayout);
    shell.setText(BaseMessages.getString(PKG, "ActionMailValidatorDialog.Title"));

    int middle = props.getMiddlePct();
    int margin = PropsUi.getMargin();

    // Buttons go at the very bottom
    //
    Button wOk = new Button(shell, SWT.PUSH);
    wOk.setText(BaseMessages.getString(PKG, "System.Button.OK"));
    wOk.addListener(SWT.Selection, e -> ok());
    Button wCancel = new Button(shell, SWT.PUSH);
    wCancel.setText(BaseMessages.getString(PKG, "System.Button.Cancel"));
    wCancel.addListener(SWT.Selection, e -> cancel());
    BaseTransformDialog.positionBottomButtons(shell, new Button[] {wOk, wCancel}, margin, null);

    // Filename line
    Label wlName = new Label(shell, SWT.RIGHT);
    wlName.setText(BaseMessages.getString(PKG, "ActionMailValidatorDialog.Label"));
    PropsUi.setLook(wlName);
    FormData fdlName = new FormData();
    fdlName.left = new FormAttachment(0, 0);
    fdlName.right = new FormAttachment(middle, -margin);
    fdlName.top = new FormAttachment(0, margin);
    wlName.setLayoutData(fdlName);
    wName = new Text(shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
    PropsUi.setLook(wName);
    wName.addModifyListener(lsMod);
    FormData fdName = new FormData();
    fdName.left = new FormAttachment(middle, 0);
    fdName.top = new FormAttachment(0, margin);
    fdName.right = new FormAttachment(100, 0);
    wName.setLayoutData(fdName);

    // eMail address
    wMailAddress =
        new LabelTextVar(
            variables,
            shell,
            BaseMessages.getString(PKG, "ActionMailValidatorDialog.MailAddress.Label"),
            BaseMessages.getString(PKG, "ActionMailValidatorDialog.MailAddress.Tooltip"));
    wMailAddress.addModifyListener(lsMod);
    FormData fdMailAddress = new FormData();
    fdMailAddress.left = new FormAttachment(0, 0);
    fdMailAddress.top = new FormAttachment(wName, margin);
    fdMailAddress.right = new FormAttachment(100, 0);
    wMailAddress.setLayoutData(fdMailAddress);

    // ////////////////////////
    // START OF Settings GROUP
    // ////////////////////////

    Group wSettingsGroup = new Group(shell, SWT.SHADOW_NONE);
    PropsUi.setLook(wSettingsGroup);
    wSettingsGroup.setText(
        BaseMessages.getString(PKG, "ActionMailValidatorDialog.Group.SettingsAddress.Label"));

    FormLayout settingsgroupLayout = new FormLayout();
    settingsgroupLayout.marginWidth = 10;
    settingsgroupLayout.marginHeight = 10;
    wSettingsGroup.setLayout(settingsgroupLayout);

    // perform SMTP check?
    Label wlSMTPCheck = new Label(wSettingsGroup, SWT.RIGHT);
    wlSMTPCheck.setText(BaseMessages.getString(PKG, "ActionMailValidatorDialog.SMTPCheck.Label"));
    PropsUi.setLook(wlSMTPCheck);
    FormData fdlSMTPCheck = new FormData();
    fdlSMTPCheck.left = new FormAttachment(0, 0);
    fdlSMTPCheck.top = new FormAttachment(wMailAddress, margin);
    fdlSMTPCheck.right = new FormAttachment(middle, -2 * margin);
    wlSMTPCheck.setLayoutData(fdlSMTPCheck);
    wSMTPCheck = new Button(wSettingsGroup, SWT.CHECK);
    PropsUi.setLook(wSMTPCheck);
    wSMTPCheck.setToolTipText(
        BaseMessages.getString(PKG, "ActionMailValidatorDialog.SMTPCheck.Tooltip"));
    FormData fdSMTPCheck = new FormData();
    fdSMTPCheck.left = new FormAttachment(middle, -margin);
    fdSMTPCheck.top = new FormAttachment(wlSMTPCheck, 0, SWT.CENTER);
    wSMTPCheck.setLayoutData(fdSMTPCheck);
    wSMTPCheck.addSelectionListener(
        new SelectionAdapter() {
          @Override
          public void widgetSelected(SelectionEvent e) {
            activeSMTPCheck();
          }
        });

    // TimeOut fieldname ...
    wlTimeOut = new Label(wSettingsGroup, SWT.RIGHT);
    wlTimeOut.setText(BaseMessages.getString(PKG, "ActionMailValidatorDialog.TimeOutField.Label"));
    PropsUi.setLook(wlTimeOut);
    FormData fdlTimeOut = new FormData();
    fdlTimeOut.left = new FormAttachment(0, 0);
    fdlTimeOut.right = new FormAttachment(middle, -2 * margin);
    fdlTimeOut.top = new FormAttachment(wlSMTPCheck, 2 * margin);
    wlTimeOut.setLayoutData(fdlTimeOut);
    wTimeOut = new TextVar(variables, wSettingsGroup, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
    wTimeOut.setToolTipText(
        BaseMessages.getString(PKG, "ActionMailValidatorDialog.TimeOutField.Tooltip"));
    PropsUi.setLook(wTimeOut);
    wTimeOut.addModifyListener(lsMod);
    FormData fdTimeOut = new FormData();
    fdTimeOut.left = new FormAttachment(middle, -margin);
    fdTimeOut.top = new FormAttachment(wlSMTPCheck, 2 * margin);
    fdTimeOut.right = new FormAttachment(100, 0);
    wTimeOut.setLayoutData(fdTimeOut);

    // eMailSender fieldname ...
    wleMailSender = new Label(wSettingsGroup, SWT.RIGHT);
    wleMailSender.setText(
        BaseMessages.getString(PKG, "ActionMailValidatorDialog.eMailSenderField.Label"));
    PropsUi.setLook(wleMailSender);
    FormData fdleMailSender = new FormData();
    fdleMailSender.left = new FormAttachment(0, 0);
    fdleMailSender.right = new FormAttachment(middle, -2 * margin);
    fdleMailSender.top = new FormAttachment(wTimeOut, margin);
    wleMailSender.setLayoutData(fdleMailSender);

    weMailSender = new TextVar(variables, wSettingsGroup, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
    weMailSender.setToolTipText(
        BaseMessages.getString(PKG, "ActionMailValidatorDialog.eMailSenderField.Tooltip"));
    PropsUi.setLook(weMailSender);
    weMailSender.addModifyListener(lsMod);
    FormData fdeMailSender = new FormData();
    fdeMailSender.left = new FormAttachment(middle, -margin);
    fdeMailSender.top = new FormAttachment(wTimeOut, margin);
    fdeMailSender.right = new FormAttachment(100, 0);
    weMailSender.setLayoutData(fdeMailSender);

    // DefaultSMTP fieldname ...
    wlDefaultSMTP = new Label(wSettingsGroup, SWT.RIGHT);
    wlDefaultSMTP.setText(
        BaseMessages.getString(PKG, "ActionMailValidatorDialog.DefaultSMTPField.Label"));
    PropsUi.setLook(wlDefaultSMTP);
    FormData fdlDefaultSMTP = new FormData();
    fdlDefaultSMTP.left = new FormAttachment(0, 0);
    fdlDefaultSMTP.right = new FormAttachment(middle, -2 * margin);
    fdlDefaultSMTP.top = new FormAttachment(weMailSender, margin);
    wlDefaultSMTP.setLayoutData(fdlDefaultSMTP);

    wDefaultSMTP = new TextVar(variables, wSettingsGroup, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
    wDefaultSMTP.setToolTipText(
        BaseMessages.getString(PKG, "ActionMailValidatorDialog.DefaultSMTPField.Tooltip"));
    PropsUi.setLook(wDefaultSMTP);
    wDefaultSMTP.addModifyListener(lsMod);
    FormData fdDefaultSMTP = new FormData();
    fdDefaultSMTP.left = new FormAttachment(middle, -margin);
    fdDefaultSMTP.top = new FormAttachment(weMailSender, margin);
    fdDefaultSMTP.right = new FormAttachment(100, 0);
    wDefaultSMTP.setLayoutData(fdDefaultSMTP);

    FormData fdSettingsGroup = new FormData();
    fdSettingsGroup.left = new FormAttachment(0, margin);
    fdSettingsGroup.top = new FormAttachment(wMailAddress, margin);
    fdSettingsGroup.right = new FormAttachment(100, -margin);
    fdSettingsGroup.bottom = new FormAttachment(wOk, -2 * margin);
    wSettingsGroup.setLayoutData(fdSettingsGroup);

    // ///////////////////////////////////////////////////////////
    // / END OF Settings GROUP
    // ///////////////////////////////////////////////////////////

    getData();
    activeSMTPCheck();

    BaseDialog.defaultShellHandling(shell, c -> ok(), c -> cancel());

    return action;
  }

  @Override
  public void dispose() {
    WindowProperty winprop = new WindowProperty(shell);
    props.setScreen(winprop);
    shell.dispose();
  }

  private void activeSMTPCheck() {
    wlTimeOut.setEnabled(wSMTPCheck.getSelection());
    wTimeOut.setEnabled(wSMTPCheck.getSelection());
    wlDefaultSMTP.setEnabled(wSMTPCheck.getSelection());
    wDefaultSMTP.setEnabled(wSMTPCheck.getSelection());
    wleMailSender.setEnabled(wSMTPCheck.getSelection());
    weMailSender.setEnabled(wSMTPCheck.getSelection());
  }

  /** Copy information from the meta-data input to the dialog fields. */
  public void getData() {
    wName.setText(Const.NVL(action.getName(), ""));
    wMailAddress.setText(Const.NVL(action.getEmailAddress(), ""));
    wTimeOut.setText(Const.NVL(action.getTimeout(), "0"));
    wSMTPCheck.setSelection(action.isSmtpCheck());
    wDefaultSMTP.setText(Const.NVL(action.getDefaultSMTP(), ""));
    weMailSender.setText(Const.NVL(action.getEmailSender(), ""));

    wName.selectAll();
    wName.setFocus();
  }

  private void cancel() {
    action.setChanged(changed);
    action = null;
    dispose();
  }

  private void ok() {
    if (Utils.isEmpty(wName.getText())) {
      MessageBox mb = new MessageBox(shell, SWT.OK | SWT.ICON_ERROR);
      mb.setText(BaseMessages.getString(PKG, "System.TransformActionNameMissing.Title"));
      mb.setMessage(BaseMessages.getString(PKG, "System.ActionNameMissing.Msg"));
      mb.open();
      return;
    }
    action.setName(wName.getText());
    action.setEmailAddress(wMailAddress.getText());
    action.setTimeout(wTimeOut.getText());
    action.setDefaultSMTP(wDefaultSMTP.getText());
    action.setEmailSender(weMailSender.getText());
    action.setSmtpCheck(wSMTPCheck.getSelection());
    dispose();
  }
}
