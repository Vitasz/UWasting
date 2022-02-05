namespace Killhope
{
    partial class MainForm
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
            this.IncomeBT = new System.Windows.Forms.Button();
            this.WasteBT = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // IncomeBT
            // 
            this.IncomeBT.Location = new System.Drawing.Point(12, 12);
            this.IncomeBT.Name = "IncomeBT";
            this.IncomeBT.Size = new System.Drawing.Size(382, 426);
            this.IncomeBT.TabIndex = 0;
            this.IncomeBT.Text = "Внести доходы";
            this.IncomeBT.UseVisualStyleBackColor = true;
            this.IncomeBT.Click += new System.EventHandler(this.IncomeBT_Click);
            // 
            // WasteBT
            // 
            this.WasteBT.Location = new System.Drawing.Point(400, 12);
            this.WasteBT.Name = "WasteBT";
            this.WasteBT.Size = new System.Drawing.Size(388, 426);
            this.WasteBT.TabIndex = 1;
            this.WasteBT.Text = "Внести расходы";
            this.WasteBT.UseVisualStyleBackColor = true;
            this.WasteBT.Click += new System.EventHandler(this.WasteBT_Click);
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.WasteBT);
            this.Controls.Add(this.IncomeBT);
            this.Name = "MainForm";
            this.Text = "MainForm";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.MainForm_FormClosing);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button IncomeBT;
        private System.Windows.Forms.Button WasteBT;
    }
}