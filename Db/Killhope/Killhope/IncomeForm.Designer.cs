namespace Killhope
{
    partial class IncomeForm
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
            this.IncomedbTXT = new System.Windows.Forms.ListBox();
            this.IncomeTXT = new System.Windows.Forms.TextBox();
            this.CatigotyTXT = new System.Windows.Forms.TextBox();
            this.SaveBT = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.DateTXT = new System.Windows.Forms.MaskedTextBox();
            this.DatedbTXT = new System.Windows.Forms.ListBox();
            this.CatTXT = new System.Windows.Forms.ListBox();
            this.SuspendLayout();
            // 
            // IncomedbTXT
            // 
            this.IncomedbTXT.FormattingEnabled = true;
            this.IncomedbTXT.Location = new System.Drawing.Point(376, 154);
            this.IncomedbTXT.Name = "IncomedbTXT";
            this.IncomedbTXT.Size = new System.Drawing.Size(119, 121);
            this.IncomedbTXT.TabIndex = 15;
            // 
            // IncomeTXT
            // 
            this.IncomeTXT.Location = new System.Drawing.Point(126, 193);
            this.IncomeTXT.Name = "IncomeTXT";
            this.IncomeTXT.Size = new System.Drawing.Size(229, 20);
            this.IncomeTXT.TabIndex = 12;
            // 
            // CatigotyTXT
            // 
            this.CatigotyTXT.Location = new System.Drawing.Point(126, 217);
            this.CatigotyTXT.Name = "CatigotyTXT";
            this.CatigotyTXT.Size = new System.Drawing.Size(229, 20);
            this.CatigotyTXT.TabIndex = 16;
            // 
            // SaveBT
            // 
            this.SaveBT.Location = new System.Drawing.Point(345, 335);
            this.SaveBT.Name = "SaveBT";
            this.SaveBT.Size = new System.Drawing.Size(87, 23);
            this.SaveBT.TabIndex = 18;
            this.SaveBT.Text = "Сохранить";
            this.SaveBT.UseVisualStyleBackColor = true;
            this.SaveBT.Click += new System.EventHandler(this.SaveBT_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(390, 61);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(35, 13);
            this.label1.TabIndex = 19;
            this.label1.Text = "label1";
            // 
            // DateTXT
            // 
            this.DateTXT.Location = new System.Drawing.Point(126, 243);
            this.DateTXT.Mask = "00/00/0000";
            this.DateTXT.Name = "DateTXT";
            this.DateTXT.Size = new System.Drawing.Size(229, 20);
            this.DateTXT.TabIndex = 20;
            this.DateTXT.ValidatingType = typeof(System.DateTime);
            // 
            // DatedbTXT
            // 
            this.DatedbTXT.FormattingEnabled = true;
            this.DatedbTXT.Location = new System.Drawing.Point(633, 154);
            this.DatedbTXT.Name = "DatedbTXT";
            this.DatedbTXT.Size = new System.Drawing.Size(137, 121);
            this.DatedbTXT.TabIndex = 26;
            // 
            // CatTXT
            // 
            this.CatTXT.FormattingEnabled = true;
            this.CatTXT.Location = new System.Drawing.Point(501, 154);
            this.CatTXT.Name = "CatTXT";
            this.CatTXT.Size = new System.Drawing.Size(126, 121);
            this.CatTXT.TabIndex = 25;
            // 
            // IncomeForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.DatedbTXT);
            this.Controls.Add(this.CatTXT);
            this.Controls.Add(this.DateTXT);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.SaveBT);
            this.Controls.Add(this.CatigotyTXT);
            this.Controls.Add(this.IncomedbTXT);
            this.Controls.Add(this.IncomeTXT);
            this.Name = "IncomeForm";
            this.Text = "IncomeForm";
            this.Load += new System.EventHandler(this.IncomeForm_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.ListBox IncomedbTXT;
        private System.Windows.Forms.TextBox IncomeTXT;
        private System.Windows.Forms.TextBox CatigotyTXT;
        private System.Windows.Forms.Button SaveBT;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.MaskedTextBox DateTXT;
        private System.Windows.Forms.ListBox DatedbTXT;
        private System.Windows.Forms.ListBox CatTXT;
    }
}