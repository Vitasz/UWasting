namespace Killhope
{
    partial class RegForm
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
            this.PasswordTXT = new System.Windows.Forms.TextBox();
            this.LoginTXT = new System.Windows.Forms.TextBox();
            this.PasswordSecTXT = new System.Windows.Forms.TextBox();
            this.AgeTXT = new System.Windows.Forms.TextBox();
            this.SurnameTXT = new System.Windows.Forms.TextBox();
            this.NameTXT = new System.Windows.Forms.TextBox();
            this.RegistrationBT = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // PasswordTXT
            // 
            this.PasswordTXT.Location = new System.Drawing.Point(272, 211);
            this.PasswordTXT.Name = "PasswordTXT";
            this.PasswordTXT.PasswordChar = '*';
            this.PasswordTXT.Size = new System.Drawing.Size(229, 20);
            this.PasswordTXT.TabIndex = 3;
            // 
            // LoginTXT
            // 
            this.LoginTXT.Location = new System.Drawing.Point(272, 185);
            this.LoginTXT.Name = "LoginTXT";
            this.LoginTXT.Size = new System.Drawing.Size(229, 20);
            this.LoginTXT.TabIndex = 2;
            // 
            // PasswordSecTXT
            // 
            this.PasswordSecTXT.Location = new System.Drawing.Point(272, 237);
            this.PasswordSecTXT.Name = "PasswordSecTXT";
            this.PasswordSecTXT.PasswordChar = '*';
            this.PasswordSecTXT.Size = new System.Drawing.Size(229, 20);
            this.PasswordSecTXT.TabIndex = 4;
            // 
            // AgeTXT
            // 
            this.AgeTXT.Location = new System.Drawing.Point(272, 159);
            this.AgeTXT.Name = "AgeTXT";
            this.AgeTXT.Size = new System.Drawing.Size(229, 20);
            this.AgeTXT.TabIndex = 7;
            // 
            // SurnameTXT
            // 
            this.SurnameTXT.Location = new System.Drawing.Point(272, 133);
            this.SurnameTXT.Name = "SurnameTXT";
            this.SurnameTXT.Size = new System.Drawing.Size(229, 20);
            this.SurnameTXT.TabIndex = 6;
            // 
            // NameTXT
            // 
            this.NameTXT.Location = new System.Drawing.Point(272, 107);
            this.NameTXT.Name = "NameTXT";
            this.NameTXT.Size = new System.Drawing.Size(229, 20);
            this.NameTXT.TabIndex = 5;
            // 
            // RegistrationBT
            // 
            this.RegistrationBT.Location = new System.Drawing.Point(345, 300);
            this.RegistrationBT.Name = "RegistrationBT";
            this.RegistrationBT.Size = new System.Drawing.Size(87, 23);
            this.RegistrationBT.TabIndex = 8;
            this.RegistrationBT.Text = "Регистрация";
            this.RegistrationBT.UseVisualStyleBackColor = true;
            this.RegistrationBT.Click += new System.EventHandler(this.RegistrationBT_Click);
            // 
            // RegForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.RegistrationBT);
            this.Controls.Add(this.AgeTXT);
            this.Controls.Add(this.SurnameTXT);
            this.Controls.Add(this.NameTXT);
            this.Controls.Add(this.PasswordSecTXT);
            this.Controls.Add(this.PasswordTXT);
            this.Controls.Add(this.LoginTXT);
            this.Name = "RegForm";
            this.Text = "RegForm";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox PasswordTXT;
        private System.Windows.Forms.TextBox LoginTXT;
        private System.Windows.Forms.TextBox PasswordSecTXT;
        private System.Windows.Forms.TextBox AgeTXT;
        private System.Windows.Forms.TextBox SurnameTXT;
        private System.Windows.Forms.TextBox NameTXT;
        private System.Windows.Forms.Button RegistrationBT;
    }
}