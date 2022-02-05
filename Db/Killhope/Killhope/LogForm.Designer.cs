namespace Killhope
{
    partial class LogForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.LoginTXT = new System.Windows.Forms.TextBox();
            this.PasswordTXT = new System.Windows.Forms.TextBox();
            this.EnterBT = new System.Windows.Forms.Button();
            this.RegistrationBT = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // LoginTXT
            // 
            this.LoginTXT.Location = new System.Drawing.Point(251, 168);
            this.LoginTXT.Name = "LoginTXT";
            this.LoginTXT.Size = new System.Drawing.Size(229, 20);
            this.LoginTXT.TabIndex = 0;
            // 
            // PasswordTXT
            // 
            this.PasswordTXT.Location = new System.Drawing.Point(251, 215);
            this.PasswordTXT.Name = "PasswordTXT";
            this.PasswordTXT.PasswordChar = '*';
            this.PasswordTXT.Size = new System.Drawing.Size(229, 20);
            this.PasswordTXT.TabIndex = 1;
            // 
            // EnterBT
            // 
            this.EnterBT.Location = new System.Drawing.Point(391, 310);
            this.EnterBT.Name = "EnterBT";
            this.EnterBT.Size = new System.Drawing.Size(75, 23);
            this.EnterBT.TabIndex = 2;
            this.EnterBT.Text = "Войти";
            this.EnterBT.UseVisualStyleBackColor = true;
            this.EnterBT.Click += new System.EventHandler(this.EnterBT_Click);
            // 
            // RegistrationBT
            // 
            this.RegistrationBT.Location = new System.Drawing.Point(251, 310);
            this.RegistrationBT.Name = "RegistrationBT";
            this.RegistrationBT.Size = new System.Drawing.Size(87, 23);
            this.RegistrationBT.TabIndex = 3;
            this.RegistrationBT.Text = "Регистрация";
            this.RegistrationBT.UseVisualStyleBackColor = true;
            this.RegistrationBT.Click += new System.EventHandler(this.RegistrationBT_Click);
            // 
            // LogForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(760, 450);
            this.Controls.Add(this.RegistrationBT);
            this.Controls.Add(this.EnterBT);
            this.Controls.Add(this.PasswordTXT);
            this.Controls.Add(this.LoginTXT);
            this.Name = "LogForm";
            this.Text = "Form1";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox LoginTXT;
        private System.Windows.Forms.TextBox PasswordTXT;
        private System.Windows.Forms.Button EnterBT;
        private System.Windows.Forms.Button RegistrationBT;
    }
}

