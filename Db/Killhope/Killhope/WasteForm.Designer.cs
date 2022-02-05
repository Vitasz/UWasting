namespace Killhope
{
    partial class WasteForm
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
            this.CatigotyTXT = new System.Windows.Forms.TextBox();
            this.WasteTXT = new System.Windows.Forms.TextBox();
            this.WastedbTXT = new System.Windows.Forms.ListBox();
            this.SaveBT = new System.Windows.Forms.Button();
            this.DateTXT = new System.Windows.Forms.MaskedTextBox();
            this.CatTXT = new System.Windows.Forms.ListBox();
            this.DatedbTXT = new System.Windows.Forms.ListBox();
            this.SuspendLayout();
            // 
            // CatigotyTXT
            // 
            this.CatigotyTXT.Location = new System.Drawing.Point(99, 206);
            this.CatigotyTXT.Name = "CatigotyTXT";
            this.CatigotyTXT.Size = new System.Drawing.Size(229, 20);
            this.CatigotyTXT.TabIndex = 9;
            // 
            // WasteTXT
            // 
            this.WasteTXT.Location = new System.Drawing.Point(99, 180);
            this.WasteTXT.Name = "WasteTXT";
            this.WasteTXT.Size = new System.Drawing.Size(229, 20);
            this.WasteTXT.TabIndex = 8;
            // 
            // WastedbTXT
            // 
            this.WastedbTXT.FormattingEnabled = true;
            this.WastedbTXT.Location = new System.Drawing.Point(389, 167);
            this.WastedbTXT.Name = "WastedbTXT";
            this.WastedbTXT.Size = new System.Drawing.Size(124, 121);
            this.WastedbTXT.TabIndex = 11;
            // 
            // SaveBT
            // 
            this.SaveBT.Location = new System.Drawing.Point(340, 346);
            this.SaveBT.Name = "SaveBT";
            this.SaveBT.Size = new System.Drawing.Size(87, 23);
            this.SaveBT.TabIndex = 12;
            this.SaveBT.Text = "Сохранить";
            this.SaveBT.UseVisualStyleBackColor = true;
            this.SaveBT.Click += new System.EventHandler(this.SaveBT_Click);
            // 
            // DateTXT
            // 
            this.DateTXT.Location = new System.Drawing.Point(99, 232);
            this.DateTXT.Mask = "00/00/0000";
            this.DateTXT.Name = "DateTXT";
            this.DateTXT.Size = new System.Drawing.Size(229, 20);
            this.DateTXT.TabIndex = 21;
            this.DateTXT.ValidatingType = typeof(System.DateTime);
            // 
            // CatTXT
            // 
            this.CatTXT.FormattingEnabled = true;
            this.CatTXT.Location = new System.Drawing.Point(519, 167);
            this.CatTXT.Name = "CatTXT";
            this.CatTXT.Size = new System.Drawing.Size(126, 121);
            this.CatTXT.TabIndex = 22;
            // 
            // DatedbTXT
            // 
            this.DatedbTXT.FormattingEnabled = true;
            this.DatedbTXT.Location = new System.Drawing.Point(651, 167);
            this.DatedbTXT.Name = "DatedbTXT";
            this.DatedbTXT.Size = new System.Drawing.Size(137, 121);
            this.DatedbTXT.TabIndex = 23;
            // 
            // WasteForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.DatedbTXT);
            this.Controls.Add(this.CatTXT);
            this.Controls.Add(this.DateTXT);
            this.Controls.Add(this.SaveBT);
            this.Controls.Add(this.WastedbTXT);
            this.Controls.Add(this.CatigotyTXT);
            this.Controls.Add(this.WasteTXT);
            this.Name = "WasteForm";
            this.Text = "WasteForm";
            this.Load += new System.EventHandler(this.WasteForm_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.TextBox CatigotyTXT;
        private System.Windows.Forms.TextBox WasteTXT;
        private System.Windows.Forms.ListBox WastedbTXT;
        private System.Windows.Forms.Button SaveBT;
        private System.Windows.Forms.MaskedTextBox DateTXT;
        private System.Windows.Forms.ListBox CatTXT;
        private System.Windows.Forms.ListBox DatedbTXT;
    }
}