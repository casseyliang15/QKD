from tkinter import *
from tkinter import messagebox
from tkinter import font
import matlab.engine
from matlab.engine import MatlabExecutionError

R=0

def calculate():
	"""
	Function to take user input and calculate key rate.
	"""
	try:
		mu0 = float(mu0_input.get())
		mu1 = float(mu1_input.get())
		mu2 = float(mu2_input.get())
		mu3= float(mu3_input.get())
		nmax = float(nmax_input.get())
		distance = float(distance_input.get())
		fiber_loss = float(fiberLoss_input.get())
		excess_noise = float(excessNoise_input.get())
		eff = float(detEff_input.get())
		threshold = float(threshold_input.get())
		qrange = float(qRange_input.get())
		res= float(res_input.get())
		mmax = float(mmax_input.get())
		global R
		eng = matlab.engine.start_matlab()
		try:
			eng.cd('matlab/DCV')
		except MatlabExecutionError:
			messagebox.showerror("Error", "File doesn't exist or unable to open Matlab")
		R= eng.compute(matlab.double(mu0),matlab.double(mu1),matlab.double(mu2),matlab.double(mu3), \
			matlab.double(nmax), matlab.double(distance),matlab.double(fiber_loss), \
			matlab.double(excess_noise), matlab.double(eff),matlab.double(threshold), \
			matlab.double(qrange),matlab.double(res),matlab.double(mmax))
		eng.quit()
		print(R)
		R_label.delete(0, END)
		R_label.insert(0, str(R))
	except ValueError:
		messagebox.showerror("Error", "Please enter valid numbers")


def open_popup():
    """
	Function to open the instruction window.
    """
    popup = Toplevel(root)
    popup.title("Instruction")
    popup.geometry("550x750")

    description = "This is the description for the parameters. " \
					"\n\n 1. mu_0, mu_1, mu_2, mu3" \
					"\n Mean photon number of the 4 different intensity levels for decoy state." \
					"\n Please enter your value in decimal ranging from 0.0001 to 1.5." \
					"\n\n 2. nmax" \
					"\n Photon number cut-off of Alice's source" \
					"\n Please enter your value in integer from 3 to 15." \
					"\n\n 3. distance" \
					"\n Distance between Alice to Bob in km" \
					"\n Please enter your value in in decimal ranging from 0 to 15." \
					"\n\n 4. Fiber loss" \
					"\n Fiber loss of the quantum channel specified in dB/km" \
					"\n Please enter your value in in decimal ranging from 0 to 0.5." \
					"\n\n 5. Excess noise" \
					"\n Excess noise specified in shot-noise unit" \
					"\n Please enter your value in in decimal ranging from 0 to 0.02" \
					"\n\n 6. Detector efficiency" \
					"\n Bob's homodyne detector effective efficiency" \
					"\n Please enter your value in in decimal ranging from 0.6 to 1" \
					"\n\n 7. Threshold" \
					"\n Decoding threshold for behaviour" \
					"\n Please enter your value in in decimal ranging from 1 to 3" \
					"\n\n 8. Quadrature range" \
					"\n Cut-off value of quadrature measured by Bob" \
					"\n Please enter your value in in integer from 4 to 10" \
					"\n\n 9. Resolution" \
					"\n Number of bin for fine-grain statistics" \
					"\n Please enter your value in in integer from 4 to 10" \
					"\n\n 10. mmax" \
					"\n Photon number cutoff Bob's detector" \
					"\n Please enter your value in in integer from 3 to 20" \
					"\n\n The following parameters can be use as a default value" \
					"\n mu_0, mu_1, mu_2, mu_3 = 1, 0.5, 0.2, 0.1" \
					"\n nmax = 15" \
					"\n distance = 0" \
					"\n fiber loss = 0.2" \
					"\n excess noise = 0" \
					"\n detector efficiency = 0.72" \
					"\n quadrature range = 8" \
					"\n resolution = 8" \
					"\n mmax = 20" \
					"\n\n Please refer to the following paper for more details:" \
					"\n Discrete-variable quantum key distribution with homodyne detection" \
					"\n https://arxiv.org/abs/2109.00492" \


    # add some widgets to the window
    text = Text(popup, wrap="word", height=20, width=50)
    text.pack(fill="both", expand=True)
    text.insert("5.0", description)
    text.config(state="disabled")

root = Tk() #root widget

helv = font.Font(family='Helvetica', size=12)
root.option_add("*Font", helv)

root.title("DCV application") #window title

root.geometry('600x400') #size of the window
root.resizable(False, False)

inputParameter_label = Label(root, text="Input parameter", font=("Helvetica", 16), anchor='center')
inputParameter_label.pack(pady=10)

# source parameter

Alice_label = Label(root, text="Alice")
Alice_label.place(x=100, y=50)

mu0_label = Label(root, text="mu_0")
mu0_label.place(x=40, y=70)

mu0_input = Entry(root) #create an input text field widget in the root application
mu0_input.place(x=80, y=70, width=80)

mu1_label = Label(root, text="mu_1")
mu1_label.place(x=40, y=100)

mu1_input = Entry(root) #create an input text field widget in the root application
mu1_input.place(x=80, y=100, width=80)

mu2_label = Label(root, text="mu_2")
mu2_label.place(x=40, y=130)

mu2_input = Entry(root) #create an input text field widget in the root application
mu2_input.place(x=80, y=130, width=80)

mu3_label = Label(root, text="mu_3")
mu3_label.place(x=40, y=160)

mu3_input = Entry(root) #create an input text field widget in the root application
mu3_input.place(x=80, y=160, width=80)

nmax_label = Label(root, text="nmax")
nmax_label.place(x=40, y=190)

nmax_input = Entry(root) #create an input text field widget in the root application
nmax_input.place(x=80, y=190, width=80)

# channel parameter

Channel_label = Label(root, text="Channel")
Channel_label.place(x=250, y=50)

distance_label = Label(root, text="distance")
distance_label.place(x=170, y=70)

distance_input = Entry(root) #create an input text field widget in the root application
distance_input.place(x=250, y=70, width=80)

fiberLoss_label = Label(root, text="fiber loss")
fiberLoss_label.place(x=170, y=100)

fiberLoss_input = Entry(root) #create an input text field widget in the root application
fiberLoss_input.place(x=250, y=100, width=80)

excessNoise_label = Label(root, text="excess noise")
excessNoise_label.place(x=170, y=130)

excessNoise_input = Entry(root) #create an input text field widget in the root application
excessNoise_input.place(x=250, y=130, width=80)

# detector parameter

Bob_label = Label(root, text="Bob")
Bob_label.place(x=350, y=50)

detEff_label = Label(root, text="detector efficiency")
detEff_label.place(x=350, y=70)

detEff_input = Entry(root) #create an input text field widget in the root application
detEff_input.place(x=470, y=70, width=80)

threshold_label = Label(root, text="threshold")
threshold_label.place(x=350, y=100)

threshold_input = Entry(root) #create an input text field widget in the root application
threshold_input.place(x=470, y=100, width=80)

qRange_label = Label(root, text="quadrature range")
qRange_label.place(x=350, y=130)

qRange_input = Entry(root) #create an input text field widget in the root application
qRange_input.place(x=470, y=130, width=80)

res_label = Label(root, text="resolution")
res_label.place(x=350, y=160)

res_input = Entry(root) #create an input text field widget in the root application
res_input.place(x=470, y=160, width=80)

mmax_label = Label(root, text="mmax")
mmax_label.place(x=350, y=190)

mmax_input = Entry(root) #create an input text field widget in the root application
mmax_input.place(x=470, y=190, width=80)

# calculate button

calculate_button = Button(root, text="Calculate key rate", command=calculate)
calculate_button.place(x=250, y=250)

R_label = Entry(root)
R_label.place(x=250, y=300)

keyRate_label = Label(root, text="key rate")
keyRate_label.place(x=200, y=300)

# add a button to the main window that opens the pop-up

button = Button(root, text="Instruction", command=open_popup)
button.place(x=250, y=350)


root.mainloop()
